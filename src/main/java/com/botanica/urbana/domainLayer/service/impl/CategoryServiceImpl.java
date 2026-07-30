package com.botanica.urbana.domainLayer.service.impl;

import com.botanica.urbana.dataAccessLayer.repository.CategoryRepository;
import com.botanica.urbana.domainLayer.entity.CategoryEntity;
import com.botanica.urbana.domainLayer.service.CategoryService;
import com.botanica.urbana.presentationLayer.dto.response.CategoryResponseDto;
import com.botanica.urbana.supportLayer.exception.ResourceNotFoundException;
import com.botanica.urbana.supportLayer.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementación de solo lectura para el servicio de categorías.
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponseDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryEntity getCategoryEntityById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría", "id", id));
    }
}