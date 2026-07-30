package com.botanica.urbana.presentationLayer.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO que captura y valida los datos enviados desde el formulario
 * para la creación o edición de categorías de productos.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryRequestDto {

    /**
     * Nombre único de la categoría (ej. "Plantas de Interior").
     */
    @NotBlank(message = "El nombre de la categoría es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre de la categoría debe tener entre 2 y 100 caracteres")
    private String name;

    /**
     * Descripción opcional de la categoría.
     */
    @Size(max = 1000, message = "La descripción no puede exceder los 1000 caracteres")
    private String description;
}
