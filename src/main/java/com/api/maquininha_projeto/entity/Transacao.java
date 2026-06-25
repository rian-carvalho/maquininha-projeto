package com.api.maquininha_projeto.entity;

import com.api.maquininha_projeto.enuns.StatusTransacao;
import com.api.maquininha_projeto.enuns.TipoTransacao;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table (name = "transacao")
public class Transacao {

    @Id
    @GeneratedValue (strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column
    @DecimalMin(value = "0.01", message = "Valor deve ser maior que 0")
    @NotNull (message = "Valor nao pode ser nulo")
    private BigDecimal valor;

    @Column
    @Enumerated (EnumType.STRING)
    @NotNull (message = "Tipo de transacao é obrigatorio!")
    private TipoTransacao tipoTransacao;

    @Column
    @Enumerated(EnumType.STRING)
    @NotNull (message = "Status da transacao é obrigatorio!")
    private StatusTransacao status;

    @ManyToOne
    @JoinColumn(name = "cartao_id")
    private Cartao cartao;

    @ManyToOne
    @JoinColumn (name = "estabelecimento_id")
    private Estabelecimento estabelecimento;

    @Column
    private LocalDateTime dataCriacao;

    @PrePersist
    public void prePersist() {
        this.dataCriacao = LocalDateTime.now();
    }
}
