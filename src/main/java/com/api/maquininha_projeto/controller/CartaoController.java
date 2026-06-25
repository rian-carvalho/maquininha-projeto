package com.api.maquininha_projeto.controller;

import com.api.maquininha_projeto.dto.CartaoRequest;
import com.api.maquininha_projeto.dto.CartaoResponse;
import com.api.maquininha_projeto.service.CartaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cartao")
public class CartaoController {

    private final CartaoService cartaoService;

    public CartaoController(CartaoService cartaoService) {
        this.cartaoService = cartaoService;
    }

    @PostMapping
    public ResponseEntity<CartaoResponse> createCartao(@RequestBody CartaoRequest cartaoRequest) {
        CartaoResponse cartaoResponse = cartaoService.createCartao(cartaoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartaoResponse);
    }

    @GetMapping
    public ResponseEntity<List<CartaoResponse>> getAllCartao() {
        List<CartaoResponse> cartaoResponseList = cartaoService.findAllCartao();
        return ResponseEntity.status(HttpStatus.OK).body(cartaoResponseList);
    }

    @GetMapping ("/{id}")
    public ResponseEntity<CartaoResponse> getCartaoById(@PathVariable(value = "id") Long idCartao) {
        CartaoResponse cartaoResponse = cartaoService.findCartaoById(idCartao);
        return ResponseEntity.status(HttpStatus.OK).body(cartaoResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartaoById(@PathVariable(value = "id") Long idCartao) {
        cartaoService.deleteCartaoById(idCartao);
        return ResponseEntity.noContent().build();
    }

}
