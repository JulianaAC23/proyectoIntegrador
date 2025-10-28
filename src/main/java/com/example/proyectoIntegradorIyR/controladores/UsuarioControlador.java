package com.example.proyectoIntegradorIyR.controladores;

import com.example.proyectoIntegradorIyR.ayudas.Roles;
import com.example.proyectoIntegradorIyR.excepciones.RecursoNoEncontradoException;
import com.example.proyectoIntegradorIyR.modelos.Usuario;
import com.example.proyectoIntegradorIyR.modelos.dtos.UsuarioDTO;
import com.example.proyectoIntegradorIyR.servicios.UsuarioServicio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio servicio;

    // 🔹 HU09: POST /usuarios → Crear usuario
    @PostMapping
    public ResponseEntity<?> crearUsuario(@Valid @RequestBody Usuario usuario, BindingResult result) {
        try {
            if (result.hasErrors()) {
                Map<String, String> errores = new HashMap<>();
                result.getFieldErrors().forEach(error ->
                        errores.put(error.getField(), error.getDefaultMessage()));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
            }

            if (usuario.getContrasena().length() < 6) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "La contraseña debe tener mínimo 6 caracteres"));
            }

            UsuarioDTO usuarioCreado = servicio.crearUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCreado);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // 🔹 HU09: GET /usuarios → Listar todos
    @GetMapping
    public ResponseEntity<?> listarUsuarios() {
        try {
            List<UsuarioDTO> usuarios = servicio.listarUsuarios();
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // 🔹 HU09: GET /usuarios/{id} → Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        try {
            UsuarioDTO usuario = servicio.obtenerPorId(id);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // 🔹 HU14: PUT /usuarios/{id} → Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Integer id,
                                               @Valid @RequestBody Usuario usuario,
                                               BindingResult result) {
        try {
            if (result.hasErrors()) {
                Map<String, String> errores = new HashMap<>();
                result.getFieldErrors().forEach(error ->
                        errores.put(error.getField(), error.getDefaultMessage()));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
            }

            if (usuario.getContrasena().length() < 6) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "La contraseña debe tener mínimo 6 caracteres"));
            }

            UsuarioDTO actualizado = servicio.actualizarUsuario(id, usuario);
            return ResponseEntity.ok(actualizado);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // 🔹 HU15: DELETE /usuarios/{id} → Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Integer id) {
        try {
            String mensaje = servicio.eliminarUsuario(id);
            return ResponseEntity.ok(Map.of("mensaje", mensaje));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // 🔹 HU19: GET /usuarios/rol/{rol} → Listar usuarios por rol
    @GetMapping("/rol/{rol}")
    public ResponseEntity<?> listarPorRol(@PathVariable Roles rol) {
        try {
            List<UsuarioDTO> usuarios = servicio.buscarUsuariosPorRol(rol);
            return ResponseEntity.ok(usuarios);
        } catch (RecursoNoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }
}


