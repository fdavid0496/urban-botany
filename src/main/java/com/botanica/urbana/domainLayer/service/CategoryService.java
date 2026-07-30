package com.botanica.urbana.domainLayer.service;

import com.botanica.urbana.domainLayer.entity.CategoryEntity;
import com.botanica.urbana.presentationLayer.dto.response.CategoryResponseDto;

import java.util.List;

/**
 * Servicio de solo lectura para la consulta de categorías de productos.
 */
public interface CategoryService {

    /**
     * Obtiene todas las categorías para listarlas en formularios y filtros.
     */
    List<CategoryResponseDto> getAllCategories();

    /**
     * Obtiene la entidad CategoryEntity por ID (para asociarla a un Producto).
     */
    CategoryEntity getCategoryEntityById(Long id);
}