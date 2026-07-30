package com.botanica.urbana.presentationLayer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO de respuesta que representa la vista completa del carrito de compras.
 * Cumple con el Requisito 3 al incluir los datos del usuario logueado,
 * la lista de ítems agregados y el total general a pagar.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponseDto {

    /**
     * Identificador único del carrito de compras.
     */
    private Long id;

    /**
     * Identificador único del usuario dueño del carrito.
     */
    private Long userId;

    /**
     * Nombre completo del usuario logueado.
     */
    private String userFullName;

    /**
     * Correo electrónico del usuario logueado.
     */
    private String userEmail;

    /**
     * Lista detallada de productos/ítems en el carrito.
     */
    @Builder.Default
    private List<CartItemResponseDto> items = new ArrayList<>();

    /**
     * Monto total a pagar (suma de los subtotales de todos los ítems).
     */
    private BigDecimal totalAmount;

    /**
     * Conteo total de productos individuales guardados en el carrito.
     */
    private Integer totalItems;
}