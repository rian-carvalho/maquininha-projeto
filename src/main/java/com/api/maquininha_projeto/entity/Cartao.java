package com.api.maquininha_projeto.entity;

import com.api.maquininha_projeto.enuns.BandeirasCartao;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table (name = "cartao")
public class Cartao {

    @Id
    @GeneratedValue (strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column (length = 19)
    @NotBlank (message = "Numero nao pode ser nulo")
    private String numero;

    @Column
    @Enumerated (EnumType.STRING)
    @NotNull(message = "Bandeira do cartao nao pode ser vazia")
    private BandeirasCartao bandeira;

    @Column
    @NotBlank (message = "Titular do cartao nao pode ser vazio")
    private String titular;
}
