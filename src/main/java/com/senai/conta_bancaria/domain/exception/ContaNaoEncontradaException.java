package com.senai.conta_bancaria.domain.exception;

public class ContaNaoEncontradaException extends RuntimeException {
    public ContaNaoEncontradaException(Long idConta) {
        super("Conta com o id "+idConta+" não foi encontrada");
    }
}
