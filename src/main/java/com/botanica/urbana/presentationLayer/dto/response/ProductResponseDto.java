package com.botanica.urbana.presentationLayer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * DTO de respuesta que contiene la información estructurada de un producto
 * para ser mostrada en el catálogo público y en el panel de administración.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDto {

    /**
     * Identificador único del producto.
     */
    private Long id;

    /**
     * Nombre comercial del producto o planta.
     */
    private String name;

    /**
     * Descripción detallada del producto.
     */
    private String description;

    /**
     * Precio del producto.
     */
    private BigDecimal price;

    /**
     * Ruta o URL de la imagen ilustrativa.
     */
    private String imageUrl;

    /**
     * Identificador de la categoría a la que pertenece.
     */
    private Long categoryId;

    /**
     * Nombre de la categoría asociada (ej. "Plantas de Interior").
     */
    private String categoryName;
}