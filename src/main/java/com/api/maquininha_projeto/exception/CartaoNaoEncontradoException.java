package com.api.maquininha_projeto.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// CartaoNaoEncontradoException.java
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CartaoNaoEncontradoException extends RuntimeException {
    public CartaoNaoEncontradoException(String msg) { super(msg); }
}
