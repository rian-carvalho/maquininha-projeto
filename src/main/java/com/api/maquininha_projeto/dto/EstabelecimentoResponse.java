package com.api.maquininha_projeto.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstabelecimentoResponse {

    private Long id;
    private String nome;
    private String cnpj;
    private String cidade;

}
