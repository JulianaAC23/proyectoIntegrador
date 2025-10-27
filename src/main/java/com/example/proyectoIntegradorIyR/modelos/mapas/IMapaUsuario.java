package com.example.proyectoIntegradorIyR.modelos.mapas;

import com.example.proyectoIntegradorIyR.modelos.Usuario;
import com.example.proyectoIntegradorIyR.modelos.dtos.UsuarioDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface IMapaUsuario {

    // Mapea de Entidad → DTO
    @Mapping(source = "id", target = "id")
    @Mapping(source = "nombre", target = "nombre")
    @Mapping(source = "rol", target = "rol")
    @Mapping(source = "estado", target = "estado")
    UsuarioDTO convertir_a_dto(Usuario usuario);

    // Mapea una lista completa
    List<UsuarioDTO> convertir_lista_a_dto(List<Usuario> lista);

    // (Opcional) Si quieres el mapeo inverso
    @Mapping(source = "id", target = "id")
    @Mapping(source = "nombre", target = "nombre")
    @Mapping(source = "rol", target = "rol")
    @Mapping(source = "estado", target = "estado")
    Usuario convertir_a_entidad(UsuarioDTO dto);
}

