package com.api.maquininha_projeto.mapper;


import com.api.maquininha_projeto.dto.EstabelecimentoRequest;
import com.api.maquininha_projeto.dto.EstabelecimentoResponse;
import com.api.maquininha_projeto.entity.Estabelecimento;
import org.springframework.stereotype.Component;

@Component
public class MapperEstabelecimento {

    public Estabelecimento toEntity(EstabelecimentoRequest estabelecimentoRequest) {
        Estabelecimento estabelecimento = new Estabelecimento();

        estabelecimento.setNome(estabelecimentoRequest.getNome());
        estabelecimento.setCnpj(estabelecimentoRequest.getCnpj());
        estabelecimento.setCidade(estabelecimentoRequest.getCidade());

        return estabelecimento;
    }

    public EstabelecimentoResponse toResponse(Estabelecimento estabelecimento) {
        EstabelecimentoResponse responseEstabelecimento = new EstabelecimentoResponse();
        responseEstabelecimento.setId(estabelecimento.getId());
        responseEstabelecimento.setNome(estabelecimento.getNome());
        responseEstabelecimento.setCnpj(estabelecimento.getCnpj());
        responseEstabelecimento.setCidade(estabelecimento.getCidade());
        return responseEstabelecimento;
    }
}
