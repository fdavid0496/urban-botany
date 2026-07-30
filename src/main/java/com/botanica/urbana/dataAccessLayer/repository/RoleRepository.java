package com.botanica.urbana.dataAccessLayer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.botanica.urbana.domainLayer.entity.RoleEntity;

/**
 * Repositorio Spring Data JPA para la entidad RoleEntity.
 * Proporciona operaciones de acceso a datos sobre la tabla 'roles'.
 */
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    
    /**
     * Busca un rol por su nombre exacto (ej. "ROLE_ADMIN", "ROLE_USER").
     *
     * @param name Nombre del rol a buscar.
     * @return Un Optional conteniendo la entidad RoleEntity si existe.
     */
    Optional<RoleEntity> findByName(String name);

    /**
     * Comprueba si un rol ya existe registrado por su nombre.
     *
     * @param name Nombre del rol a verificar.
     * @return true si el rol existe, false en caso contrario.
     */
    boolean existsByName(String name);
}
