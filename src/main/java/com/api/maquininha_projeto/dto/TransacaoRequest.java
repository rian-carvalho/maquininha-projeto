package com.api.maquininha_projeto.dto;

import com.api.maquininha_projeto.enuns.TipoTransacao;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransacaoRequest {

    @DecimalMin(value = "0.01", message = "Valor deve ser maior que 0")
    @NotNull(message = "Valor nao pode ser nulo")
    private BigDecimal valor;

    @NotNull (message = "Tipo de transacao é obrigatorio!")
    private TipoTransacao tipoTransacao;

    @NotNull(message = "Cartao é obrigatorio!")
    private Long cartaoId;

    @NotNull(message = "Estabelecimento é obrigatorio!")
    private Long estabelecimentoId;

}
