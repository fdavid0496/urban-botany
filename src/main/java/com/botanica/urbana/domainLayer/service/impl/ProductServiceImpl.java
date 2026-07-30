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
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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

    private static final String UPLOAD_DIRECTORY = "src/main/resources/static/images/products/";
    private static final String DEFAULT_PLACEHOLDER = "/images/products/placeholder.jpg";

    @Override
    @Transactional
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        if (productRepository.existsByName(productRequestDto.getName())) {
            throw new BadRequestException("Ya existe un producto registrado con el nombre '" + productRequestDto.getName() + "'.");
        }

        CategoryEntity category = categoryService.getCategoryEntityById(productRequestDto.getCategoryId());
        ProductEntity productEntity = productMapper.toEntity(productRequestDto, category);

        // Si el usuario seleccionó un archivo desde su equipo, lo procesa y guarda
        if (productRequestDto.getImageFile() != null && !productRequestDto.getImageFile().isEmpty()) {
            String savedImagePath = saveImageToDisk(productRequestDto.getImageFile());
            productEntity.setImageUrl(savedImagePath);
        } else if (productEntity.getImageUrl() == null || productEntity.getImageUrl().trim().isEmpty()) {
            productEntity.setImageUrl(DEFAULT_PLACEHOLDER);
        }

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
        String currentImageUrl = existingProduct.getImageUrl();

        productMapper.updateEntityFromDto(productRequestDto, existingProduct, category);

        // Si se seleccionó un nuevo archivo desde el equipo, se guarda la nueva foto
        if (productRequestDto.getImageFile() != null && !productRequestDto.getImageFile().isEmpty()) {
            String newSavedImagePath = saveImageToDisk(productRequestDto.getImageFile());
            existingProduct.setImageUrl(newSavedImagePath);
        } else {
            // Conserva la imagen previa si no se seleccionó un nuevo archivo
            existingProduct.setImageUrl(currentImageUrl);
        }

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

    /**
     * Guarda físicamente el archivo de imagen subido desde la computadora en la carpeta de recursos del proyecto.
     * Genera un nombre único con UUID para evitar colisiones de archivos.
     *
     * @param imageFile Archivo de imagen subido.
     * @return Ruta relativa pública para guardar en MySQL y servir en Thymeleaf.
     */
    private String saveImageToDisk(MultipartFile imageFile) {
        try {
            Path uploadPath = Paths.get(UPLOAD_DIRECTORY);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalFilename = StringUtils.cleanPath(Objects.requireNonNull(imageFile.getOriginalFilename()));
            String uniqueFilename = UUID.randomUUID().toString() + "_" + originalFilename;

            Path filePath = uploadPath.resolve(uniqueFilename);
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return "/images/products/" + uniqueFilename;
        } catch (IOException e) {
            throw new BadRequestException("No se pudo guardar la imagen seleccionada desde el equipo. Error: " + e.getMessage());
        }
    }
}