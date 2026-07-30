package com.botanica.urbana.dataAccessLayer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.botanica.urbana.domainLayer.entity.CategoryEntity;

/**
 * Repositorio Spring Data JPA para la entidad CategoryEntity.
 * Proporciona operaciones de acceso a datos sobre la tabla 'categories'.
 */
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    
    /**
     * Busca una categoría por su nombre exacto (ej. "Plantas de Interior").
     *
     * @param name Nombre de la categoría a buscar.
     * @return Un Optional conteniendo la entidad CategoryEntity si existe.
     */
    Optional<CategoryEntity> findByName(String name);

    /**
     * Comprueba si ya existe una categoría registrada con el nombre proporcionado.
     *
     * @param name Nombre de la categoría a verificar.
     * @return true si la categoría existe, false en caso contrario.
     */
    boolean existsByName(String name);
}
