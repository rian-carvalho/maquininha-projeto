package com.api.maquininha_projeto.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "estabelecimento")
public class Estabelecimento {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank (message = "Nome nao pode ser nulo")
    private String nome;

    @Column (unique = true)
    @NotBlank (message = "CNPJ nao pode ser nulo")
    private String cnpj;

    @Column
    @NotBlank (message = "Cidade nao pode ser nula")
    private String cidade;
}
