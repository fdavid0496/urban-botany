package com.botanica.urbana.dataAccessLayer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.botanica.urbana.domainLayer.entity.ProductEntity;

/**
 * Repositorio Spring Data JPA para la entidad ProductEntity.
 * Proporciona operaciones de acceso a datos sobre la tabla 'products'.
 */
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    
    /**
     * Obtiene la lista de productos pertenecientes a una categoría específica por su ID.
     *
     * @param categoryId Identificador de la categoría.
     * @return Lista de productos asociados a la categoría.
     */
    List<ProductEntity> findByCategoryId(Long categoryId);

    /**
     * Obtiene la lista de productos pertenecientes a una categoría por su nombre.
     *
     * @param categoryName Nombre de la categoría.
     * @return Lista de productos filtrados.
     */
    List<ProductEntity> findByCategoryName(String categoryName);

    /**
     * Busca productos cuyo nombre contenga una palabra clave (búsqueda insensible a mayúsculas/minúsculas).
     *
     * @param keyword Término de búsqueda.
     * @return Lista de productos que coinciden con el término.
     */
    List<ProductEntity> findByNameContainingIgnoreCase(String keyword);

    /**
     * Comprueba si ya existe un producto registrado con el mismo nombre.
     *
     * @param name Nombre del producto.
     * @return true si el producto existe, false en caso contrario.
     */
    boolean existsByName(String name);
}
