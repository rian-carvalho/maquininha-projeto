package com.api.maquininha_projeto.mapper;

import com.api.maquininha_projeto.dto.CartaoRequest;
import com.api.maquininha_projeto.dto.CartaoResponse;
import com.api.maquininha_projeto.entity.Cartao;
import org.springframework.stereotype.Component;

@Component
public class MapperCartao {

    public Cartao toEntity (CartaoRequest request) {
        String numeroMascarado = "**** **** **** " + request.getNumero().substring(request.getNumero().length() - 4);
        Cartao cartao = new Cartao();
        cartao.setTitular(request.getTitular());
        cartao.setNumero(numeroMascarado);
        cartao.setBandeira(request.getBandeira());
        return cartao;
    }

    public CartaoResponse toResponse(Cartao cartao) {
        CartaoResponse cartaoResponse = new CartaoResponse();
        cartaoResponse.setId(cartao.getId());
        cartaoResponse.setTitular(cartao.getTitular());
        cartaoResponse.setNumeroMascarado(cartao.getNumero());
        cartaoResponse.setBandeira(cartao.getBandeira());
        return cartaoResponse;
    }

}
