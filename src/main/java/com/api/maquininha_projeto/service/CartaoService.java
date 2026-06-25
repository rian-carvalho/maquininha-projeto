package com.api.maquininha_projeto.service;

import com.api.maquininha_projeto.dto.CartaoRequest;
import com.api.maquininha_projeto.dto.CartaoResponse;
import com.api.maquininha_projeto.entity.Cartao;
import com.api.maquininha_projeto.mapper.MapperCartao;
import com.api.maquininha_projeto.repository.CartaoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartaoService {

    private final CartaoRepository cartaoRepository;
    private final MapperCartao mapperCartao;

    public CartaoService(CartaoRepository cartaoRepository, MapperCartao mapperCartao) {
        this.mapperCartao = mapperCartao;
        this.cartaoRepository = cartaoRepository;
    }

    public CartaoResponse createCartao(CartaoRequest request) {
        Cartao cartao = mapperCartao.toEntity(request);
        cartaoRepository.save(cartao);

        return mapperCartao.toResponse(cartao);
    }

    public CartaoResponse findCartaoById(Long id) {
        Cartao cartao = cartaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cartão não encontrado"));

        return mapperCartao.toResponse(cartao);
    }

    public List<CartaoResponse> findAllCartao() {
        List<Cartao> cartaoList = cartaoRepository.findAll();

        List <CartaoResponse> cartaoResponseList = new ArrayList<>();

        for (Cartao cartao : cartaoList) {
            cartaoResponseList.add(mapperCartao.toResponse(cartao));
        }
        return cartaoResponseList;
    }

    public void deleteCartaoById(Long id) {
        Cartao cartao = cartaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cartão não encontrado"));

         cartaoRepository.delete(cartao);
        }
    }


