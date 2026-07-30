package com.botanica.urbana.presentationLayer.controller;

import com.botanica.urbana.domainLayer.service.CartService;
import com.botanica.urbana.presentationLayer.dto.request.AddToCartRequestDto;
import com.botanica.urbana.presentationLayer.dto.response.CartResponseDto;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

/**
 * Controlador Spring MVC para la gestión interactiva del carrito de compras.
 * Cumple con el Requisito 3 del laboratorio (persistencia de datos por usuario,
 * visualización del usuario logueado, lista de productos y monto total a
 * pagar).
 */
@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    /**
     * Muestra la vista del carrito de compras con todos los productos del usuario
     * autenticado.
     */
    @GetMapping
    public String showCart(Model model, Principal principal) {
        CartResponseDto cart = cartService.getCartByUserEmail(principal.getName());
        model.addAttribute("cart", cart);
        return "cart/cart-view";
    }

    /**
     * Agrega un producto al carrito de compras desde el catálogo o vista de
     * detalle.
     */
    @PostMapping("/add")
    public String addToCart(
            @Valid @ModelAttribute("addToCartRequestDto") AddToCartRequestDto addToCartRequestDto,
            BindingResult bindingResult,
            Principal principal,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Error al agregar el producto. Verifique la cantidad.");
            return "redirect:/products";
        }

        cartService.addProductToCart(principal.getName(), addToCartRequestDto);
        redirectAttributes.addFlashAttribute("successMessage", "¡Producto agregado al carrito exitosamente!");
        return "redirect:/cart";
    }

    /**
     * Modifica la cantidad de unidades de un ítem existente en el carrito.
     */
    @PostMapping("/update")
    public String updateQuantity(
            @RequestParam("cartItemId") Long cartItemId,
            @RequestParam("quantity") Integer quantity,
            Principal principal,
            RedirectAttributes redirectAttributes) {

        cartService.updateCartItemQuantity(principal.getName(), cartItemId, quantity);
        redirectAttributes.addFlashAttribute("successMessage", "Cantidad actualizada correctamente.");
        return "redirect:/cart";
    }

    /**
     * Elimina un ítem específico del carrito de compras.
     */
    @GetMapping("/remove/{itemId}")
    public String removeItem(@PathVariable("itemId") Long itemId, Principal principal,
            RedirectAttributes redirectAttributes) {
        cartService.removeCartItem(principal.getName(), itemId);
        redirectAttributes.addFlashAttribute("successMessage", "Producto eliminado del carrito.");
        return "redirect:/cart";
    }

    /**
     * Vacía completamente el carrito de compras del usuario.
     */
    @PostMapping("/clear")
    public String clearCart(Principal principal, RedirectAttributes redirectAttributes) {
        cartService.clearCart(principal.getName());
        redirectAttributes.addFlashAttribute("successMessage", "Carrito vaciado exitosamente.");
        return "redirect:/cart";
    }
}