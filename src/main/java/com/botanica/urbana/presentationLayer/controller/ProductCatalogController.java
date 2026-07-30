package com.botanica.urbana.presentationLayer.controller;

import com.botanica.urbana.domainLayer.service.CategoryService;
import com.botanica.urbana.domainLayer.service.ProductService;
import com.botanica.urbana.presentationLayer.dto.response.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Controlador Spring MVC para la galería y consulta pública del catálogo de
 * productos.
 * Maneja las vistas principales, filtrado por categorías, buscador y detalle
 * del producto.
 */
@Controller
@RequiredArgsConstructor
public class ProductCatalogController {

    private final ProductService productService;
    private final CategoryService categoryService;

    /**
     * Muestra la vista principal del catálogo con todas las plantas y artículos
     * disponibles.
     */
    @GetMapping({ "/", "/products" })
    public String showCatalog(Model model) {
        List<ProductResponseDto> products = productService.getAllProducts();
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "products/catalog";
    }

    /**
     * Filtra los productos del catálogo según la categoría seleccionada por el
     * usuario.
     */
    @GetMapping("/products/category/{categoryId}")
    public String showProductsByCategory(@PathVariable Long categoryId, Model model) {
        List<ProductResponseDto> products = productService.getProductsByCategory(categoryId);
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("selectedCategoryId", categoryId);
        return "products/catalog";
    }

    /**
     * Realiza la búsqueda de productos en el catálogo por término o palabra clave.
     */
    @GetMapping("/products/search")
    public String searchProducts(@RequestParam(required = false) String keyword, Model model) {
        List<ProductResponseDto> products = productService.searchProductsByName(keyword);
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("keyword", keyword);
        return "products/catalog";
    }

    /**
     * Muestra la vista de detalle de una planta o artículo específico.
     */
    @GetMapping("/products/{id}")
    public String showProductDetail(@PathVariable Long id, Model model) {
        ProductResponseDto product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "products/product-detail";
    }
}