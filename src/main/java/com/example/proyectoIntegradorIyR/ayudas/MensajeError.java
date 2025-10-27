package com.example.proyectoIntegradorIyR.ayudas;

public enum MensajeError {
    USUARIO_NO_ENCONTRADO(" Usuario no encontrado"),
    ERROR_GENERAL_API("Error en la ejecución del servicio: ");

    private final String descripcion;

    MensajeError(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}

