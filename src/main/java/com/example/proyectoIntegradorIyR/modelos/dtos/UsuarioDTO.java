package com.example.proyectoIntegradorIyR.modelos.dtos;

import com.example.proyectoIntegradorIyR.ayudas.Estados;
import com.example.proyectoIntegradorIyR.ayudas.Roles;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UsuarioDTO {

    private Integer id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede superar los 50 caracteres")
    private String nombre;

    @NotNull(message = "El rol es obligatorio")
    private Roles rol;

    @NotNull(message = "El estado es obligatorio")
    private Estados estado;

    public UsuarioDTO() {
    }

    public UsuarioDTO(Integer id, String nombre, Roles rol, Estados estado) {
        this.id = id;
        this.nombre = nombre;
        this.rol = rol;
        this.estado = estado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Roles getRol() {
        return rol;
    }

    public void setRol(Roles rol) {
        this.rol = rol;
    }

    public Estados getEstado() {
        return estado;
    }

    public void setEstado(Estados estado) {
        this.estado = estado;
    }
}

