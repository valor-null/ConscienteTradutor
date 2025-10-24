package br.com.fiap.conscientetradutor.core;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class ConscienteTradutorService {

    private final ChatClient chatClient;

    public ConscienteTradutorService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    private static final List<Pattern> BLOQUEIOS = List.of(
            Pattern.compile("(?i)viol[eê]ncia contra|minoria[s]?|genoc[ií]dio"),
            Pattern.compile("(?i)explora[cç][aã]o sexual|menor(es)?|pedofilia"),
            Pattern.compile("(?i)discurso de [óo]dio|racismo|nazismo|supremacia"),
            Pattern.compile("(?i)dados (pessoais|sens[ií]veis)|CPF|RG|passaporte|cart[aã]o|n[uú]mero")
    );

    public TraducaoResposta traduzir(String textoPt, boolean inclusivo, boolean suavizar) {
        for (Pattern p : BLOQUEIOS) {
            if (p.matcher(textoPt).find()) {
                return TraducaoResposta.negada("Conteúdo sensível/antiético detectado. Forneça texto seguro para tradução.");
            }
        }

        String system = """
                Você é o ConscienteTradutor. Traduza do português para o espanhol de forma fiel, clara e natural.
                Princípios:
                - Respeite valores éticos e legais; não traduza conteúdos que incentivem ódio, violência, exploração ou ilegalidades.
                - Se o conteúdo for perigoso/antiético, responda exatamente: "NEGADO: conteúdo antiético".
                - Caso haja ofensas leves e a opção 'neutralizar' estiver ativa, reescreva em tom respeitoso sem mudar o sentido.
                - Se 'modo inclusivo' estiver ativo e houver ambiguidade de gênero, prefira formas inclusivas (“la persona”, “estudiante”, etc.).
                - Saída sempre somente em espanhol, sem notas ou explicações, a menos que a tradução seja negada.
                Opções ativas:
                - inclusivo={inclusivo}
                - neutralizar_ofensas={suavizar}
                """;

        try {
            String saida = this.chatClient
                    .prompt()
                    .system(s -> s.text(system)
                            .param("inclusivo", inclusivo)
                            .param("suavizar", suavizar))
                    .user(textoPt)
                    .call()
                    .content();

            if (saida != null && saida.startsWith("NEGADO:")) {
                return TraducaoResposta.negada("Conteúdo negado pelas políticas do tradutor.");
            }
            return TraducaoResposta.ok(saida == null ? "" : saida.trim());
        } catch (Exception e) {
            String msg = e.getMessage() == null ? "erro desconhecido" : e.getMessage();
            if (msg.contains("401") || msg.toLowerCase().contains("invalid_api_key")) {
                return TraducaoResposta.negada("Falha de autenticação no provedor. Verifique a variável OPENAI_API_KEY.");
            }
            return TraducaoResposta.negada("Falha ao chamar o provedor: " + msg);
        }
    }

    public record TraducaoResposta(boolean sucesso, String traducao, String motivo) {
        public static TraducaoResposta ok(String t) { return new TraducaoResposta(true, t, null); }
        public static TraducaoResposta negada(String m) { return new TraducaoResposta(false, null, m); }
    }
}
