package br.com.fiap.conscientetradutor.web.dto;

import lombok.Data;

@Data
public class TraducaoDTO {
    private String textoOriginal;
    private boolean modoInclusivo;
    private boolean neutralizarOfensas;
}
