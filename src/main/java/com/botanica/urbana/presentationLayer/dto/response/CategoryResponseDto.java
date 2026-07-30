package com.botanica.urbana.presentationLayer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO de respuesta que contiene la información estructurada de una categoría.
 * Utilizado para cargar filtros en el catálogo público, listas desplegables
 * y en la administración del sitio.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponseDto {

    /**
     * Identificador único de la categoría.
     */
    private Long id;

    /**
     * Nombre de la categoría (ej. "Macetas y Jardineras").
     */
    private String name;

    /**
     * Descripción explicativa de la categoría.
     */
    private String description;
}