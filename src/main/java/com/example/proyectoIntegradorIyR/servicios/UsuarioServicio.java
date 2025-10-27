package com.example.proyectoIntegradorIyR.servicios;

import com.example.proyectoIntegradorIyR.ayudas.MensajeError;
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

    // Crear Usuario
    public UsuarioDTO crearUsuario(Usuario datosUsuario) throws Exception {
        try {
            return this.mapa.convertir_a_dto(this.repositorio.save(datosUsuario));
        } catch (Exception error) {
            throw new Exception(MensajeError.ERROR_GENERAL_API.getDescripcion() + error.getMessage());
        }
    }

    // Listar todos los usuarios
    public List<UsuarioDTO> listarUsuarios() throws Exception {
        try {
            return this.mapa.convertir_lista_a_dto(this.repositorio.findAll());
        } catch (Exception error) {
            throw new Exception(MensajeError.ERROR_GENERAL_API.getDescripcion() + error.getMessage());
        }
    }

    // Obtener un usuario por ID
    public UsuarioDTO obtenerPorId(Integer idUsuario) throws Exception {
        try {
            Optional<Usuario> usuarioEncontrado = this.repositorio.findById(idUsuario);
            if (usuarioEncontrado.isPresent()) {
                return this.mapa.convertir_a_dto(usuarioEncontrado.get());
            } else {
                throw new Exception(MensajeError.USUARIO_NO_ENCONTRADO.getDescripcion());
            }
        } catch (Exception error) {
            throw new Exception(MensajeError.ERROR_GENERAL_API.getDescripcion() + error.getMessage());
        }
    }

    // Actualizar un usuario
    public UsuarioDTO actualizarUsuario(Integer id, Usuario datosActualizados) throws Exception {
        try {
            Optional<Usuario> usuarioExistente = this.repositorio.findById(id);
            if (usuarioExistente.isPresent()) {
                Usuario usuario = usuarioExistente.get();
                usuario.setNombre(datosActualizados.getNombre());
                usuario.setCorreo(datosActualizados.getCorreo());
                usuario.setContrasena(datosActualizados.getContrasena());
                usuario.setRol(datosActualizados.getRol());
                usuario.setEstado(datosActualizados.getEstado());
                return this.mapa.convertir_a_dto(this.repositorio.save(usuario));
            } else {
                throw new Exception(MensajeError.USUARIO_NO_ENCONTRADO.getDescripcion());
            }
        } catch (Exception error) {
            throw new Exception(MensajeError.ERROR_GENERAL_API.getDescripcion() + error.getMessage());
        }
    }

    // Eliminar un usuario
    public String eliminarUsuario(Integer id) throws Exception {
        try {
            Optional<Usuario> usuarioEncontrado = this.repositorio.findById(id);
            if (usuarioEncontrado.isPresent()) {
                this.repositorio.deleteById(id);
                return "Usuario eliminado exitosamente";
            } else {
                throw new Exception(MensajeError.USUARIO_NO_ENCONTRADO.getDescripcion());
            }
        } catch (Exception error) {
            throw new Exception(MensajeError.ERROR_GENERAL_API.getDescripcion() + error.getMessage());
        }
    }
}
