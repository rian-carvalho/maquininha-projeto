package com.api.maquininha_projeto.controller;

import com.api.maquininha_projeto.dto.EstabelecimentoRequest;
import com.api.maquininha_projeto.dto.EstabelecimentoResponse;
import com.api.maquininha_projeto.service.EstabelecimentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/estabelecimento")
public class EstabelecimentoController {

    private final EstabelecimentoService estabelecimentoService;

    public EstabelecimentoController(EstabelecimentoService estabelecimentoService) {
        this.estabelecimentoService = estabelecimentoService;
    }

    @PostMapping
    public ResponseEntity <EstabelecimentoResponse> create (@RequestBody EstabelecimentoRequest estabelecimentoRequest){
        EstabelecimentoResponse estabelecimentoResponse = estabelecimentoService.createEstabelecimento(estabelecimentoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(estabelecimentoResponse);
    }

    @GetMapping
    public ResponseEntity<List<EstabelecimentoResponse>> findAll(){
        List<EstabelecimentoResponse> estabelecimentoResponseList = estabelecimentoService.findAllEstabelecimentos();
        return ResponseEntity.status(HttpStatus.OK).body(estabelecimentoResponseList);
    }

    @GetMapping ("/{id}")
    public ResponseEntity<EstabelecimentoResponse> findById(@PathVariable(value = "id") Long id){
        EstabelecimentoResponse estabelecimentoResponse = estabelecimentoService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(estabelecimentoResponse);
    }

    @PutMapping ("/{id}")
    public ResponseEntity <EstabelecimentoResponse> updateById(@PathVariable (value = "id") Long id, @RequestBody EstabelecimentoRequest estabelecimentoRequest){
        EstabelecimentoResponse estabelecimentoResponse = estabelecimentoService.updateById(id, estabelecimentoRequest);
        return ResponseEntity.status(HttpStatus.OK).body(estabelecimentoResponse);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstabelecimentoById(@PathVariable(value = "id") Long idEstabelecimento) {
        estabelecimentoService.deleteById(idEstabelecimento);
        return ResponseEntity.noContent().build();
    }


}
