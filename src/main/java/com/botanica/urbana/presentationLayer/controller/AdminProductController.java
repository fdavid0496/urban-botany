package com.botanica.urbana.presentationLayer.controller;

import com.botanica.urbana.domainLayer.service.CategoryService;
import com.botanica.urbana.domainLayer.service.ProductService;
import com.botanica.urbana.presentationLayer.dto.request.ProductRequestDto;
import com.botanica.urbana.presentationLayer.dto.response.ProductResponseDto;
import com.botanica.urbana.supportLayer.exception.BadRequestException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador Spring MVC para la gestión de productos del Administrador (CRUD).
 * Cumple con el Requisito 2 del laboratorio, restringido al rol ADMIN.
 */
@Controller
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    /**
     * Muestra la tabla de administración con todos los productos registrados.
     */
    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "admin/product-list";
    }

    /**
     * Muestra el formulario para crear un nuevo producto.
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("productRequestDto", new ProductRequestDto());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/product-form";
    }

    /**
     * Procesa la creación de un nuevo producto, procesando el archivo de imagen adjunto.
     */
    @PostMapping
    public String processCreateProduct(
            @Valid @ModelAttribute("productRequestDto") ProductRequestDto productRequestDto,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "admin/product-form";
        }

        try {
            productService.createProduct(productRequestDto);
            redirectAttributes.addFlashAttribute("successMessage", "¡Producto creado y guardado exitosamente!");
            return "redirect:/admin/products";
        } catch (BadRequestException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("categories", categoryService.getAllCategories());
            return "admin/product-form";
        }
    }

    /**
     * Muestra el formulario cargado con los datos de un producto existente para su edición.
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        ProductResponseDto product = productService.getProductById(id);

        ProductRequestDto dto = ProductRequestDto.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .categoryId(product.getCategoryId())
                .build();

        model.addAttribute("productRequestDto", dto);
        model.addAttribute("productId", id);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/product-form";
    }

    /**
     * Procesa la actualización de un producto existente y reemplaza su imagen si se selecciona una nueva.
     */
    @PostMapping("/edit/{id}")
    public String processUpdateProduct(
            @PathVariable Long id,
            @Valid @ModelAttribute("productRequestDto") ProductRequestDto productRequestDto,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("productId", id);
            model.addAttribute("categories", categoryService.getAllCategories());
            return "admin/product-form";
        }

        try {
            productService.updateProduct(id, productRequestDto);
            redirectAttributes.addFlashAttribute("successMessage", "¡Producto actualizado exitosamente!");
            return "redirect:/admin/products";
        } catch (BadRequestException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("productId", id);
            model.addAttribute("categories", categoryService.getAllCategories());
            return "admin/product-form";
        }
    }

    /**
     * Elimina un producto por su identificador único.
     */
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        productService.deleteProduct(id);
        redirectAttributes.addFlashAttribute("successMessage", "¡Producto eliminado correctamente!");
        return "redirect:/admin/products";
    }
}