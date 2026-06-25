package com.api.maquininha_projeto.dto;

import com.api.maquininha_projeto.enuns.StatusTransacao;
import com.api.maquininha_projeto.enuns.TipoTransacao;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class TransacaoResponse {
    private Long id;
    private BigDecimal valor;
    private TipoTransacao tipoTransacao;
    private StatusTransacao status;
    private LocalDateTime dataCriacao;
    private String numeroCartaoMascarado;
    private String nomeEstabelecimento;
}
