package com.api.maquininha_projeto.controller;

import com.api.maquininha_projeto.dto.TransacaoRequest;
import com.api.maquininha_projeto.dto.TransacaoResponse;
import com.api.maquininha_projeto.service.TransacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @PostMapping
    public ResponseEntity<TransacaoResponse> processar(@RequestBody TransacaoRequest request) {
        TransacaoResponse response = transacaoService.processar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<TransacaoResponse>> listarTodas() {
        List<TransacaoResponse> responses = transacaoService.listarTodas();
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransacaoResponse> buscarPorId(@PathVariable Long id) {
        TransacaoResponse response = transacaoService.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/{id}/estorno")
    public ResponseEntity<TransacaoResponse> estornar(@PathVariable Long id) {
        TransacaoResponse response = transacaoService.estornar(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}