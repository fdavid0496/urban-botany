package com.botanica.urbana.dataAccessLayer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.botanica.urbana.domainLayer.entity.UserEntity;

/**
 * Repositorio Spring Data JPA para la entidad UserEntity.
 * Proporciona operaciones de acceso a datos sobre la tabla 'users'.
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    
    /**
     * Busca un usuario por su correo electrónico (utilizado para el inicio de sesión).
     *
     * @param email Correo electrónico del usuario.
     * @return Un Optional conteniendo la entidad UserEntity si existe.
     */
    Optional<UserEntity> findByEmail(String email);

    /**
     * Comprueba si ya existe un usuario registrado con el correo proporcionado.
     *
     * @param email Correo electrónico a verificar.
     * @return true si el correo ya está registrado, false en caso contrario.
     */
    boolean existsByEmail(String email);
}
