package com.example.proyectoIntegradorIyR.repositorios;

import com.example.proyectoIntegradorIyR.modelos.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IEstudianteReposiotrio extends JpaRepository<Estudiante, Long> {

    List<Estudiante> findByPromedio(Double promedio);
    
}
