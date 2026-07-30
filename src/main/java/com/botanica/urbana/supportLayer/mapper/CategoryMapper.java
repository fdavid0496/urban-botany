package com.botanica.urbana.supportLayer.mapper;

import com.botanica.urbana.domainLayer.entity.CategoryEntity;
import com.botanica.urbana.presentationLayer.dto.request.CategoryRequestDto;
import com.botanica.urbana.presentationLayer.dto.response.CategoryResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Componente Mapeador encargado de la conversión entre la entidad
 * CategoryEntity
 * y sus correspondientes DTOs de petición y respuesta.
 */
@Component
@RequiredArgsConstructor
public class CategoryMapper {

    private final ModelMapper modelMapper;

    /**
     * Convierte un DTO de petición de categoría a una entidad CategoryEntity.
     *
     * @param dto DTO con los datos ingresados en el formulario.
     * @return Entidad CategoryEntity mapeada.
     */
    public CategoryEntity toEntity(CategoryRequestDto dto) {
        return modelMapper.map(dto, CategoryEntity.class);
    }

    /**
     * Convierte una entidad CategoryEntity a su DTO de respuesta.
     *
     * @param entity Entidad de la categoría desde la base de datos.
     * @return DTO CategoryResponseDto listo para renderizar en las vistas.
     */
    public CategoryResponseDto toResponseDto(CategoryEntity entity) {
        return modelMapper.map(entity, CategoryResponseDto.class);
    }

    /**
     * Actualiza una entidad CategoryEntity existente con los datos de un DTO de
     * petición (para edición).
     *
     * @param dto    DTO con la nueva información de la categoría.
     * @param entity Entidad existente a modificar.
     */
    public void updateEntityFromDto(CategoryRequestDto dto, CategoryEntity entity) {
        modelMapper.map(dto, entity);
    }
}