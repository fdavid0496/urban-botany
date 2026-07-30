package com.botanica.urbana.supportLayer.mapper;

import com.botanica.urbana.domainLayer.entity.CategoryEntity;
import com.botanica.urbana.domainLayer.entity.ProductEntity;
import com.botanica.urbana.presentationLayer.dto.request.ProductRequestDto;
import com.botanica.urbana.presentationLayer.dto.response.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Componente Mapeador encargado de la conversión entre la entidad ProductEntity
 * y sus correspondientes DTOs de petición y respuesta.
 */
@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final ModelMapper modelMapper;

    /**
     * Convierte un DTO de petición a una entidad ProductEntity.
     *
     * @param dto      DTO con los datos del formulario del producto.
     * @param category Entidad de la categoría a la que pertenece el producto.
     * @return Entidad ProductEntity lista para guardar.
     */
    public ProductEntity toEntity(ProductRequestDto dto, CategoryEntity category) {
        ProductEntity product = modelMapper.map(dto, ProductEntity.class);
        product.setId(null);
        product.setCategory(category);
        return product;
    }

    /**
     * Convierte una entidad ProductEntity a su DTO de respuesta.
     * Asigna explícitamente el ID y el nombre de la categoría relacionada.
     *
     * @param entity Entidad de producto desde la base de datos.
     * @return DTO ProductResponseDto preparado para la vista.
     */
    public ProductResponseDto toResponseDto(ProductEntity entity) {
        ProductResponseDto response = modelMapper.map(entity, ProductResponseDto.class);
        if (entity.getCategory() != null) {
            response.setCategoryId(entity.getCategory().getId());
            response.setCategoryName(entity.getCategory().getName());
        }
        return response;
    }

    /**
     * Actualiza los datos de una entidad ProductEntity existente con la información
     * de un DTO.
     *
     * @param dto      DTO con los datos actualizados.
     * @param entity   Entidad existente que se modificará.
     * @param category Entidad de la categoría asignada.
     */
    public void updateEntityFromDto(ProductRequestDto dto, ProductEntity entity, CategoryEntity category) {
        modelMapper.map(dto, entity);
        entity.setCategory(category);
    }
}