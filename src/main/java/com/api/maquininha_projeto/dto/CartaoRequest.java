package com.api.maquininha_projeto.dto;

import com.api.maquininha_projeto.enuns.BandeirasCartao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartaoRequest {

    @NotBlank(message = "Numero nao pode ser nulo")
    private String numero;

    @NotNull(message = "Bandeira nao pode ser nula")
    private BandeirasCartao bandeira;

    @NotBlank(message = "Titular nao pode ser vazio")
    private String titular;
}

