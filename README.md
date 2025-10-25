# 🤖ConscienteTradutor (Português → Español)

Aplicação Spring Boot + Thymeleaf que traduz textos do português para o espanhol com princípios éticos: opção de linguagem inclusiva, neutralização de ofensas leves e recusa de conteúdos antiéticos.

## Funcionalidades

- Tradução PT → ES com modelo via Spring AI
- Modo inclusivo opcional
- Neutralização opcional de ofensas leves
- Filtro mínimo para bloquear conteúdo antiético
- Interface web simples com Thymeleaf
- Exemplos prontos para correção

## Arquitetura

- Spring Boot 3.x (Web, Thymeleaf)
- Spring AI (OpenAI) com `ChatClient`
- MVC: Controller + Service + DTO + templates

## Requisitos

- JDK 17+
- Acesso à API (OpenAI) com créditos

## Configuração de Ambiente

Defina a variável `OPENAI_API_KEY` antes de iniciar:

Windows PowerShell:
```powershell
$env:OPENAI_API_KEY="sua-chave-aqui"
.\mvnw spring-boot:run
```
após isso abra o http://localhost:8080/
## Adendo: 
- os exemplos então dentro da aplicação apos rodar ele está no proprio index.html

---
## Integrantes: 
- ⭐️ Valéria Conceição Dos Santos — RM: 557177
- ⭐️ Mirela Pinheiro Silva Rodrigues — RM: 558191
- ⭐️ Guilherme Romanholi Santos — RM: 557462


