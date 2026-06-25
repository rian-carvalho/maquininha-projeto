package com.api.maquininha_projeto.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstabelecimentoRequest {

    @NotBlank(message = "Nome nao pode ser nulo")
    private String nome;

    @NotBlank (message = "CNPJ nao pode ser nulo")
    private String cnpj;

    @NotBlank (message = "Cidade nao pode ser nula")
    private String cidade;

}
