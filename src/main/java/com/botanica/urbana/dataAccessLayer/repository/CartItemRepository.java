package com.botanica.urbana.dataAccessLayer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.botanica.urbana.domainLayer.entity.CartItemEntity;

/**
 * Repositorio Spring Data JPA para la entidad CartItemEntity.
 * Proporciona operaciones de acceso a datos sobre la tabla 'cart_items'.
 */
public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {
    
    /**
     * Busca un ítem dentro de un carrito específico por el ID del carrito y el ID del producto.
     * Permite verificar si la planta/producto ya fue agregada previamente para actualizar su cantidad.
     *
     * @param cartId Identificador del carrito.
     * @param productId Identificador del producto.
     * @return Un Optional conteniendo el ítem del carrito si existe.
     */
    Optional<CartItemEntity> findByCartIdAndProductId(Long cartId, Long productId);

    /**
     * Obtiene la lista completa de productos agregados a un carrito de compras.
     *
     * @param cartId Identificador del carrito.
     * @return Lista de ítems en el carrito.
     */
    List<CartItemEntity> findByCartId(Long cartId);

    /**
     * Elimina todos los elementos pertenecientes a un carrito específico (útil para vaciar el carrito).
     *
     * @param cartId Identificador del carrito a vaciar.
     */
    void deleteByCartId(Long cartId);
}
