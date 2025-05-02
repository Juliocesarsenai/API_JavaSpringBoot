package com.senai.JavaSpringApplication.exception;

public class RecursoNaoEncontrado extends RuntimeException {
    public RecursoNaoEncontrado(String message) {
        super(message);
    }
}