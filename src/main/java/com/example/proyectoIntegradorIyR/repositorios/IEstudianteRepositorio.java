package com.example.proyectoIntegradorIyR.repositorios;

import com.example.proyectoIntegradorIyR.modelos.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IEstudianteRepositorio extends JpaRepository<Estudiante, Integer> {

    List<Estudiante> findByPromedio(Double promedio);

}
