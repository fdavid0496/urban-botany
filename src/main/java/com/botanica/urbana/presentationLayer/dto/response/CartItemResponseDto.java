package com.botanica.urbana.presentationLayer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * DTO de respuesta que representa una fila o ítem dentro del carrito de compras.
 * Contiene la información relevante del producto, la cantidad seleccionada
 * y el subtotal acumulado.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemResponseDto {

    /**
     * Identificador único del ítem del carrito.
     */
    private Long id;

    /**
     * Identificador único del producto asociado.
     */
    private Long productId;

    /**
     * Nombre comercial del producto o planta.
     */
    private String productName;

    /**
     * Ruta o URL de la imagen del producto.
     */
    private String productImageUrl;

    /**
     * Precio unitario del producto.
     */
    private BigDecimal unitPrice;

    /**
     * Cantidad de unidades seleccionadas.
     */
    private Integer quantity;

    /**
     * Subtotal calculado para este ítem (precio unitario * cantidad).
     */
    private BigDecimal subtotal;
}