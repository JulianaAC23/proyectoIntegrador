package com.example.proyectoIntegradorIyR.excepciones;

public class RecursoNoEncontradoException extends RuntimeException {
    public RecursoNoEncontradoException(String mensaje){
        super(mensaje);
    }
}
