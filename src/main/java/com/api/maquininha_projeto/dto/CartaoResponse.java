package com.api.maquininha_projeto.dto;

import com.api.maquininha_projeto.enuns.BandeirasCartao;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartaoResponse {

    private Long id;
    private String numeroMascarado;
    private BandeirasCartao bandeira;
    private String titular;
}

