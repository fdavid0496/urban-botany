package com.botanica.urbana.domainLayer.service;

import com.botanica.urbana.domainLayer.entity.ProductEntity;
import com.botanica.urbana.presentationLayer.dto.request.ProductRequestDto;
import com.botanica.urbana.presentationLayer.dto.response.ProductResponseDto;

import java.util.List;

/**
 * Interfaz de servicio que define la lógica de negocio para la gestión de
 * productos (CRUD)
 * y la consulta del catálogo de plantas y artículos de jardinería.
 */
public interface ProductService {

    /**
     * Registra un nuevo producto en el catálogo (Función del Administrador).
     *
     * @param productRequestDto DTO con los datos del formulario de creación.
     * @return ProductResponseDto con la información del producto guardado.
     */
    ProductResponseDto createProduct(ProductRequestDto productRequestDto);

    /**
     * Actualiza un producto existente por su ID (Función del Administrador).
     *
     * @param id                Identificador del producto a modificar.
     * @param productRequestDto DTO con la información actualizada.
     * @return ProductResponseDto con los datos modificados.
     */
    ProductResponseDto updateProduct(Long id, ProductRequestDto productRequestDto);

    /**
     * Elimina un producto del catálogo por su ID (Función del Administrador).
     *
     * @param id Identificador del producto a eliminar.
     */
    void deleteProduct(Long id);

    /**
     * Obtiene los detalles de un producto por su identificador único.
     *
     * @param id Identificador del producto.
     * @return ProductResponseDto del producto encontrado.
     */
    ProductResponseDto getProductById(Long id);

    /**
     * Obtiene la entidad completa ProductEntity mediante su ID.
     * Método de utilidad interna utilizado principalmente por el servicio de
     * carrito.
     *
     * @param id Identificador del producto.
     * @return Entidad ProductEntity.
     */
    ProductEntity getProductEntityById(Long id);

    /**
     * Obtiene el listado completo de productos para el catálogo general.
     *
     * @return Lista de DTOs de respuesta de productos.
     */
    List<ProductResponseDto> getAllProducts();

    /**
     * Obtiene los productos pertenecientes a una categoría específica.
     *
     * @param categoryId Identificador de la categoría.
     * @return Lista de DTOs de productos pertenecientes a esa categoría.
     */
    List<ProductResponseDto> getProductsByCategory(Long categoryId);

    /**
     * Busca productos cuyo nombre contenga la palabra clave ingresada en la
     * búsqueda.
     *
     * @param keyword Término de búsqueda.
     * @return Lista de DTOs de productos que coinciden con el término.
     */
    List<ProductResponseDto> searchProductsByName(String keyword);
}