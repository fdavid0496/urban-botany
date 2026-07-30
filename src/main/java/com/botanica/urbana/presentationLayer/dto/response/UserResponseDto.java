package com.botanica.urbana.presentationLayer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO de respuesta que representa la información pública del usuario.
 * Utilizado para mostrar datos en encabezados y perfiles, garantizando
 * no exponer datos sensibles como la contraseña encriptada.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {

    /**
     * Identificador único del usuario.
     */
    private Long id;

    /**
     * Nombre completo del usuario.
     */
    private String fullName;

    /**
     * Correo electrónico del usuario.
     */
    private String email;

    /**
     * Nombre del rol asignado al usuario (ej. "ROLE_ADMIN" o "ROLE_USER").
     */
    private String roleName;
}