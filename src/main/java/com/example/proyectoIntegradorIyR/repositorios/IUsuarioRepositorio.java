package com.example.proyectoIntegradorIyR.repositorios;

import com.example.proyectoIntegradorIyR.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUsuarioRepositorio extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByCorreo(String correo);

}
