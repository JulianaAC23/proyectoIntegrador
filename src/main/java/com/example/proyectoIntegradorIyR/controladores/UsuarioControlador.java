package com.example.proyectoIntegradorIyR.controladores;

import com.example.proyectoIntegradorIyR.modelos.dtos.UsuarioDTO;
import com.example.proyectoIntegradorIyR.servicios.UsuarioServicio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio servicio;

    /**
     * 🟢 HU09 - Crear usuario con DTO
     * HU10 - Validar datos antes de registrar
     */
    @PostMapping
    public ResponseEntity<?> crearUsuario(
            @Valid @RequestBody UsuarioDTO datosUsuario,
            BindingResult resultadoValidacion
    ) {
        try {
            // Validar los errores del DTO
            if (resultadoValidacion.hasErrors()) {
                StringBuilder errores = new StringBuilder("Errores en la validación: ");
                resultadoValidacion.getFieldErrors().forEach(error ->
                        errores.append(String.format("[%s: %s] ", error.getField(), error.getDefaultMessage()))
                );
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(errores.toString());
            }

            // Si no hay errores, crear el usuario
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(servicio.crearUsuario(datosUsuario));

        } catch (Exception error) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Error al crear el usuario: " + error.getMessage());
        }
    }

    /**
     * 🟢 HU09 - Listar todos los usuarios
     */
    @GetMapping
    public ResponseEntity<?> listarUsuarios() {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(servicio.listarUsuarios());
        } catch (Exception error) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Error al listar usuarios: " + error.getMessage());
        }
    }

    /**
     * 🟢 HU09 - Obtener usuario por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerUsuarioPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(servicio.obtenerPorId(id));
        } catch (Exception error) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado: " + error.getMessage());
        }
    }
}

