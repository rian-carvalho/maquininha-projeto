package com.api.maquininha_projeto.mapper;

import com.api.maquininha_projeto.dto.TransacaoResponse;
import com.api.maquininha_projeto.entity.Transacao;
import org.springframework.stereotype.Component;

@Component
public class MapperTransacao {

    // Mantém dígitos apenas nos 4 últimos: "1234-5678-9012-3456" → "****-****-****-3456"
    private String mascararNumero(String numero) {
        if (numero == null || numero.length() < 4) return numero;
        String ultimos4 = numero.substring(numero.length() - 4);
        String prefixo = numero.substring(0, numero.length() - 4)
                .replaceAll("[0-9]", "*");
        return prefixo + ultimos4;
    }

    public TransacaoResponse toResponse(Transacao t) {
        TransacaoResponse response = new TransacaoResponse();
        response.setId(t.getId());
        response.setValor(t.getValor());
        response.setTipoTransacao(t.getTipoTransacao());
        response.setStatus(t.getStatus());
        response.setDataCriacao(t.getDataCriacao());
        response.setNumeroCartaoMascarado(mascararNumero(t.getCartao().getNumero()));
        response.setNomeEstabelecimento(t.getEstabelecimento().getNome());
        return response;
    }
}
