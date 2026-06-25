package com.api.maquininha_projeto.service;

import com.api.maquininha_projeto.dto.TransacaoRequest;
import com.api.maquininha_projeto.dto.TransacaoResponse;
import com.api.maquininha_projeto.entity.Cartao;
import com.api.maquininha_projeto.entity.Estabelecimento;
import com.api.maquininha_projeto.entity.Transacao;
import com.api.maquininha_projeto.enuns.StatusTransacao;
import com.api.maquininha_projeto.exception.CartaoNaoEncontradoException;
import com.api.maquininha_projeto.exception.EstabelecimentoNaoEncontradoException;
import com.api.maquininha_projeto.mapper.MapperTransacao;
import com.api.maquininha_projeto.repository.CartaoRepository;
import com.api.maquininha_projeto.repository.EstabelecimentoRepository;
import com.api.maquininha_projeto.repository.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final CartaoRepository cartaoRepository;
    private final EstabelecimentoRepository estabelecimentoRepository;
    private final TransacaoRepository transacaoRepository;
    private final MapperTransacao mapperTransacao;


    @Transactional
    public TransacaoResponse processar(TransacaoRequest request) {

        Cartao cartao = cartaoRepository.findById(request.getCartaoId())
                .orElseThrow(() -> new CartaoNaoEncontradoException(
                        "Cartão não encontrado com id: " + request.getCartaoId()));


        Estabelecimento estabelecimento = estabelecimentoRepository.findById(request.getEstabelecimentoId())
                .orElseThrow(() -> new EstabelecimentoNaoEncontradoException(
                        "Estabelecimento não encontrado com id: " + request.getEstabelecimentoId()));

        // Monta a entidade (dataCriacao é setada pelo @PrePersist)
        Transacao transacao = new Transacao();
        transacao.setValor(request.getValor());
        transacao.setTipoTransacao(request.getTipoTransacao());
        transacao.setStatus(StatusTransacao.APROVADA);
        transacao.setCartao(cartao);
        transacao.setEstabelecimento(estabelecimento);

        Transacao salva = transacaoRepository.save(transacao);
        return mapperTransacao.toResponse(salva);
    }

    public TransacaoResponse buscarPorId(Long id) {
        Transacao transacao = transacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada com id: " + id));
        return mapperTransacao.toResponse(transacao);
    }

    public List<TransacaoResponse> listarTodas() {
        return transacaoRepository.findAll()
                .stream()
                .map(mapperTransacao::toResponse)
                .toList();
    }

    @Transactional
    public TransacaoResponse estornar(Long id) {
        Transacao transacao = transacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada com id: " + id));

        if (transacao.getStatus() == StatusTransacao.ESTORNADA) {
            throw new RuntimeException("Transação já foi estornada.");
        }

        transacao.setStatus(StatusTransacao.ESTORNADA);
        return mapperTransacao.toResponse(transacaoRepository.save(transacao));
    }

}
