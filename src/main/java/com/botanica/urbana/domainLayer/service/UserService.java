package com.botanica.urbana.domainLayer.service;

import com.botanica.urbana.domainLayer.entity.UserEntity;
import com.botanica.urbana.presentationLayer.dto.request.UserRegisterRequestDto;
import com.botanica.urbana.presentationLayer.dto.response.UserResponseDto;

import java.util.List;

/**
 * Interfaz de servicio que define las operaciones de negocio para el registro,
 * consulta y verificación de usuarios dentro del sistema.
 */
public interface UserService {

    /**
     * Registra un nuevo usuario en la aplicación asignándole el rol por defecto (ROLE_USER)
     * y encriptando su contraseña mediante BCrypt.
     *
     * @param registerRequestDto DTO con la información ingresada en el formulario de registro.
     * @return UserResponseDto con los datos del usuario recién creado.
     */
    UserResponseDto registerUser(UserRegisterRequestDto registerRequestDto);

    /**
     * Obtiene la información pública de un usuario buscando por su correo electrónico.
     *
     * @param email Correo electrónico del usuario a consultar.
     * @return UserResponseDto con los datos del usuario.
     */
    UserResponseDto getUserByEmail(String email);

    /**
     * Obtiene la entidad completa UserEntity a partir de su correo electrónico.
     * Método utilizado internamente por la seguridad y la gestión del carrito de compras.
     *
     * @param email Correo electrónico del usuario.
     * @return Entidad UserEntity.
     */
    UserEntity getUserEntityByEmail(String email);

    /**
     * Verifica si un correo electrónico ya está registrado en la base de datos.
     *
     * @param email Correo electrónico a comprobar.
     * @return true si el correo ya existe, false en caso contrario.
     */
    boolean isEmailAlreadyRegistered(String email);

    /**
     * Obtiene el listado completo de usuarios registrados en el sistema.
     *
     * @return Lista de DTOs de respuesta de usuarios.
     */
    List<UserResponseDto> getAllUsers();
}