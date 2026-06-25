package com.api.maquininha_projeto.service;

import com.api.maquininha_projeto.dto.EstabelecimentoRequest;
import com.api.maquininha_projeto.dto.EstabelecimentoResponse;
import com.api.maquininha_projeto.entity.Estabelecimento;
import com.api.maquininha_projeto.mapper.MapperEstabelecimento;
import com.api.maquininha_projeto.repository.EstabelecimentoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EstabelecimentoService {

    private final EstabelecimentoRepository estabelecimentoRepository;
    private final MapperEstabelecimento mapperEstabelecimento;

    public EstabelecimentoService(EstabelecimentoRepository repository, MapperEstabelecimento mapperEstabelecimento) {
        this.estabelecimentoRepository = repository;
        this.mapperEstabelecimento = mapperEstabelecimento;
    }

    public EstabelecimentoResponse createEstabelecimento(EstabelecimentoRequest estabelecimentoRequest) {
        Estabelecimento estabelecimento = mapperEstabelecimento.toEntity(estabelecimentoRequest);
        estabelecimentoRepository.save(estabelecimento);

        return mapperEstabelecimento.toResponse(estabelecimento);
    }

    public EstabelecimentoResponse updateById(Long id, EstabelecimentoRequest estabelecimentoRequest) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estabelecimento nao encontrado"));
        estabelecimento.setNome(estabelecimentoRequest.getNome());
        estabelecimento.setCnpj(estabelecimentoRequest.getCnpj());
        estabelecimento.setCidade(estabelecimentoRequest.getCidade());
        estabelecimentoRepository.save(estabelecimento);
        return mapperEstabelecimento.toResponse(estabelecimento);
    }

    public List<EstabelecimentoResponse> findAllEstabelecimentos() {
        List<Estabelecimento> estabelecimentos = estabelecimentoRepository.findAll();

        List<EstabelecimentoResponse> estabelecimentoResponses = new ArrayList<>();

        for (Estabelecimento estabelecimento : estabelecimentos) {
            estabelecimentoResponses.add(mapperEstabelecimento.toResponse(estabelecimento));
        }
        return estabelecimentoResponses;
    }

    public EstabelecimentoResponse findById(Long id) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estabelecimento nao encontrado"));
        return mapperEstabelecimento.toResponse(estabelecimento);
    }

    public void deleteById(Long id) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estabelecimento nao encontrado"));

        estabelecimentoRepository.delete(estabelecimento);
    }

}
