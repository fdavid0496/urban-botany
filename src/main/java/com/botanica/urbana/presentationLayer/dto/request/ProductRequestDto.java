package com.botanica.urbana.presentationLayer.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO que captura y valida la información enviada desde el formulario de creación
 * o actualización de productos en el panel del Administrador (CRUD).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDto {
    
    /**
     * Nombre comercial del producto o planta.
     */
    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(min = 2, max = 150, message = "El nombre del producto debe tener entre 2 y 150 caracteres")
    private String name;

    /**
     * Descripción detallada del producto.
     */
    @Size(max = 2000, message = "La descripción no puede exceder los 2000 caracteres")
    private String description;

    /**
     * Precio unitario del producto.
     */
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0.00")
    @Digits(integer = 8, fraction = 2, message = "El precio debe tener un formato numérico válido")
    private BigDecimal price;

    /**
     * Ruta o URL de la imagen del producto.
     */
    @Size(max = 255, message = "La URL de la imagen no puede exceder los 255 caracteres")
    private String imageUrl;

    /**
     * Identificador de la categoría asociada al producto.
     */
    @NotNull(message = "Debe seleccionar una categoría para el producto")
    private Long categoryId;
}
