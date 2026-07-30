package com.botanica.urbana.presentationLayer.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO que captura y valida la información enviada desde el catálogo o vista
 * de producto para agregar un ítem al carrito de compras o modificar su cantidad.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddToCartRequestDto {

    /**
     * Identificador único del producto a agregar.
     */
    @NotNull(message = "El ID del producto es obligatorio")
    private Long productId;

    /**
     * Cantidad de unidades solicitadas (debe ser al menos 1).
     */
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad agregada debe ser de al menos 1 unidad")
    private Integer quantity;
}