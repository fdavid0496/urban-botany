package com.botanica.urbana.domainLayer.service.impl;

import com.botanica.urbana.dataAccessLayer.repository.ProductRepository;
import com.botanica.urbana.domainLayer.entity.CategoryEntity;
import com.botanica.urbana.domainLayer.entity.ProductEntity;
import com.botanica.urbana.domainLayer.service.CategoryService;
import com.botanica.urbana.domainLayer.service.ProductService;
import com.botanica.urbana.presentationLayer.dto.request.ProductRequestDto;
import com.botanica.urbana.presentationLayer.dto.response.ProductResponseDto;
import com.botanica.urbana.supportLayer.exception.BadRequestException;
import com.botanica.urbana.supportLayer.exception.ResourceNotFoundException;
import com.botanica.urbana.supportLayer.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementación del servicio ProductService.
 * Maneja la lógica de negocio para la gestión completa de productos (CRUD de Administrador)
 * y la consulta y filtrado del catálogo público.
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ProductMapper productMapper;

    @Override
    @Transactional
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        if (productRepository.existsByName(productRequestDto.getName())) {
            throw new BadRequestException("Ya existe un producto registrado con el nombre '" + productRequestDto.getName() + "'.");
        }

        CategoryEntity category = categoryService.getCategoryEntityById(productRequestDto.getCategoryId());
        ProductEntity productEntity = productMapper.toEntity(productRequestDto, category);
        ProductEntity savedProduct = productRepository.save(productEntity);

        return productMapper.toResponseDto(savedProduct);
    }

    @Override
    @Transactional
    public ProductResponseDto updateProduct(Long id, ProductRequestDto productRequestDto) {
        ProductEntity existingProduct = getProductEntityById(id);

        if (!existingProduct.getName().equalsIgnoreCase(productRequestDto.getName()) &&
                productRepository.existsByName(productRequestDto.getName())) {
            throw new BadRequestException("Ya existe un producto registrado con el nombre '" + productRequestDto.getName() + "'.");
        }

        CategoryEntity category = categoryService.getCategoryEntityById(productRequestDto.getCategoryId());
        productMapper.updateEntityFromDto(productRequestDto, existingProduct, category);

        ProductEntity updatedProduct = productRepository.save(existingProduct);
        return productMapper.toResponseDto(updatedProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        ProductEntity product = getProductEntityById(id);
        productRepository.delete(product);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDto getProductById(Long id) {
        ProductEntity product = getProductEntityById(id);
        return productMapper.toResponseDto(product);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductEntity getProductEntityById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto", "id", id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDto> getProductsByCategory(Long categoryId) {
        categoryService.getCategoryEntityById(categoryId); // Valida que la categoría exista
        return productRepository.findByCategoryId(categoryId).stream()
                .map(productMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDto> searchProductsByName(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllProducts();
        }
        return productRepository.findByNameContainingIgnoreCase(keyword.trim()).stream()
                .map(productMapper::toResponseDto)
                .toList();
    }
}