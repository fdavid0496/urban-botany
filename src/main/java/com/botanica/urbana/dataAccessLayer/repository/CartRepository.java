package com.botanica.urbana.dataAccessLayer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.botanica.urbana.domainLayer.entity.CartEntity;

/**
 * Repositorio Spring Data JPA para la entidad CartEntity.
 * Proporciona operaciones de acceso a datos sobre la tabla 'carts'.
 */
public interface CartRepository extends JpaRepository<CartEntity, Long> {
    
    /**
     * Busca el carrito activo asociado a un usuario por su ID.
     *
     * @param userId Identificador único del usuario.
     * @return Un Optional conteniendo la entidad CartEntity si existe.
     */
    Optional<CartEntity> findByUserId(Long userId);

    /**
     * Busca el carrito de compras asociado a un usuario por su correo electrónico.
     *
     * @param email Correo electrónico del usuario autenticado.
     * @return Un Optional conteniendo el carrito si existe.
     */
    Optional<CartEntity> findByUserEmail(String email);

    /**
     * Comprueba si un usuario ya posee un carrito de compras creado.
     *
     * @param userId Identificador del usuario.
     * @return true si el carrito existe, false en caso contrario.
     */
    boolean existsByUserId(Long userId);
}
