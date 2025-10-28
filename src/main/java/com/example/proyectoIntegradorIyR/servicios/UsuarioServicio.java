package com.example.proyectoIntegradorIyR.servicios;

import com.example.proyectoIntegradorIyR.ayudas.MensajeError;
import com.example.proyectoIntegradorIyR.ayudas.Roles;
import com.example.proyectoIntegradorIyR.excepciones.RecursoNoEncontradoException;
import com.example.proyectoIntegradorIyR.excepciones.ValidacionException;
import com.example.proyectoIntegradorIyR.modelos.Usuario;
import com.example.proyectoIntegradorIyR.modelos.dtos.UsuarioDTO;
import com.example.proyectoIntegradorIyR.modelos.mapas.IMapaUsuario;
import com.example.proyectoIntegradorIyR.repositorios.IUsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServicio {

    @Autowired
    private IUsuarioRepositorio repositorio;

    @Autowired
    private IMapaUsuario mapa;

    // 🔹 Crear usuario
    public UsuarioDTO crearUsuario(Usuario datosUsuario) throws Exception {
        try {
            Optional<Usuario> existente = repositorio.findByCorreo(datosUsuario.getCorreo());
            if (existente.isPresent()) {
                throw new ValidacionException("El correo ya está registrado");
            }
            return mapa.convertir_a_dto(repositorio.save(datosUsuario));
        } catch (ValidacionException e) {
            throw e;
        } catch (Exception error) {
            throw new Exception(MensajeError.ERROR_GENERAL_API.getDescripcion() + error.getMessage());
        }
    }

    // 🔹 Listar todos
    public List<UsuarioDTO> listarUsuarios() throws Exception {
        try {
            return mapa.convertir_lista_a_dto(repositorio.findAll());
        } catch (Exception error) {
            throw new Exception(MensajeError.ERROR_GENERAL_API.getDescripcion() + error.getMessage());
        }
    }

    // 🔹 Obtener por ID
    public UsuarioDTO obtenerPorId(Integer idUsuario) throws Exception {
        Usuario usuario = repositorio.findById(idUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con ID: " + idUsuario));
        return mapa.convertir_a_dto(usuario);
    }

    // 🔹 HU14 – Actualizar usuario
    public UsuarioDTO actualizarUsuario(Integer id, Usuario datosActualizados) throws Exception {
        try {
            Usuario usuarioExistente = repositorio.findById(id)
                    .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con ID: " + id));

            Optional<Usuario> usuarioConCorreo = repositorio.findByCorreo(datosActualizados.getCorreo());
            if (usuarioConCorreo.isPresent() && !usuarioConCorreo.get().getId().equals(id)) {
                throw new ValidacionException("El correo ya está en uso por otro usuario");
            }

            usuarioExistente.setNombre(datosActualizados.getNombre());
            usuarioExistente.setCorreo(datosActualizados.getCorreo());
            usuarioExistente.setContrasena(datosActualizados.getContrasena());
            usuarioExistente.setRol(datosActualizados.getRol());
            usuarioExistente.setEstado(datosActualizados.getEstado());

            Usuario actualizado = repositorio.save(usuarioExistente);
            return mapa.convertir_a_dto(actualizado);

        } catch (RecursoNoEncontradoException | ValidacionException e) {
            throw e;
        } catch (Exception error) {
            throw new Exception(MensajeError.ERROR_GENERAL_API.getDescripcion() + error.getMessage());
        }
    }

    // 🔹 HU15 – Eliminar usuario
    public String eliminarUsuario(Integer id) throws Exception {
        Usuario usuario = repositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el usuario con ID: " + id));

        repositorio.delete(usuario);
        return "Usuario eliminado exitosamente";
    }

    // 🔹 HU19 – Listar usuarios por rol
    public List<UsuarioDTO> buscarUsuariosPorRol(Roles rol) throws Exception {
        try {
            List<Usuario> usuarios = repositorio.findByRol(rol);
            if (usuarios.isEmpty()) {
                throw new RecursoNoEncontradoException("No se encontraron usuarios con el rol: " + rol);
            }
            return mapa.convertir_lista_a_dto(usuarios);
        } catch (RecursoNoEncontradoException e) {
            throw e;
        } catch (Exception error) {
            throw new Exception("Error al buscar usuarios por rol: " + error.getMessage());
        }
    }
}
