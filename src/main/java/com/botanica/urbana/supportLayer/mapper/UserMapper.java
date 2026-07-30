package com.botanica.urbana.supportLayer.mapper;

import com.botanica.urbana.domainLayer.entity.RoleEntity;
import com.botanica.urbana.domainLayer.entity.UserEntity;
import com.botanica.urbana.presentationLayer.dto.request.UserRegisterRequestDto;
import com.botanica.urbana.presentationLayer.dto.response.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Componente Mapeador encargado de la conversión entre la entidad UserEntity
 * y sus correspondientes DTOs de petición y respuesta.
 */
@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;

    /**
     * Convierte un DTO de petición de registro a una entidad UserEntity.
     *
     * @param dto  DTO con los datos ingresados en el formulario de registro.
     * @param role Entidad del rol asignado al usuario.
     * @return Entidad UserEntity mapeada.
     */
    public UserEntity toEntity(UserRegisterRequestDto dto, RoleEntity role) {
        UserEntity user = modelMapper.map(dto, UserEntity.class);
        user.setRole(role);
        return user;
    }

    /**
     * Convierte una entidad UserEntity a su DTO de respuesta.
     * Excluye información sensible como la contraseña.
     *
     * @param entity Entidad de usuario desde la base de datos.
     * @return DTO UserResponseDto formateado para la vista.
     */
    public UserResponseDto toResponseDto(UserEntity entity) {
        UserResponseDto response = modelMapper.map(entity, UserResponseDto.class);
        if (entity.getRole() != null) {
            response.setRoleName(entity.getRole().getName());
        }
        return response;
    }
}