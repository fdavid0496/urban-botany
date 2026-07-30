package com.botanica.urbana.domainLayer.service;

import com.botanica.urbana.presentationLayer.dto.request.AddToCartRequestDto;
import com.botanica.urbana.presentationLayer.dto.response.CartResponseDto;

/**
 * Interfaz de servicio que define la lógica de negocio para la gestión del carrito de compras
 * y la persistencia de productos por usuario.
 */
public interface CartService {

    /**
     * Obtiene el carrito de compras persistente del usuario autenticado.
     * Si el usuario aún no posee un carrito en la base de datos, lo crea automáticamente.
     *
     * @param userEmail Correo electrónico del usuario autenticado.
     * @return CartResponseDto con el detalle de productos, subtotales y total acumulado.
     */
    CartResponseDto getCartByUserEmail(String userEmail);

    /**
     * Agrega un producto al carrito del usuario. Si el producto ya estaba previamente en el carrito,
     * incrementa la cantidad existente.
     *
     * @param userEmail Correo electrónico del usuario.
     * @param addToCartRequestDto DTO con el ID del producto y la cantidad a añadir.
     * @return CartResponseDto actualizado.
     */
    CartResponseDto addProductToCart(String userEmail, AddToCartRequestDto addToCartRequestDto);

    /**
     * Modifica la cantidad de unidades de un producto específico en el carrito.
     *
     * @param userEmail Correo electrónico del usuario.
     * @param cartItemId Identificador del ítem en el carrito.
     * @param newQuantity Nueva cantidad de unidades solicitadas.
     * @return CartResponseDto actualizado.
     */
    CartResponseDto updateCartItemQuantity(String userEmail, Long cartItemId, Integer newQuantity);

    /**
     * Elimina un producto específico del carrito del usuario.
     *
     * @param userEmail Correo electrónico del usuario.
     * @param cartItemId Identificador del ítem a eliminar.
     * @return CartResponseDto actualizado.
     */
    CartResponseDto removeCartItem(String userEmail, Long cartItemId);

    /**
     * Vacía completamente el carrito de compras del usuario.
     *
     * @param userEmail Correo electrónico del usuario.
     * @return CartResponseDto sin ítems.
     */
    CartResponseDto clearCart(String userEmail);
}