package com.botanica.urbana.presentationLayer.controller;

import com.botanica.urbana.domainLayer.service.UserService;
import com.botanica.urbana.presentationLayer.dto.request.UserRegisterRequestDto;
import com.botanica.urbana.supportLayer.exception.BadRequestException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador Spring MVC para la gestión del inicio de sesión y registro de
 * usuarios.
 * Atiende las solicitudes a las rutas públicas '/login' y '/register'.
 */
@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    /**
     * Muestra la vista del formulario de inicio de sesión.
     * Si el usuario ya está autenticado, lo redirige automáticamente al catálogo.
     */
    @GetMapping("/login")
    public String showLoginForm() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() &&
                !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/products";
        }
        return "auth/login";
    }

    /**
     * Muestra la vista del formulario de registro de nuevos usuarios.
     * Prepara el DTO del formulario y verifica si ya existe sesión activa.
     */
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() &&
                !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/products";
        }

        if (!model.containsAttribute("userRegisterRequestDto")) {
            model.addAttribute("userRegisterRequestDto", new UserRegisterRequestDto());
        }
        return "auth/register";
    }

    /**
     * Procesa la solicitud del formulario de registro de usuarios.
     * Valida errores de entrada y captura excepciones de negocio (ej. correo
     * duplicado o contraseñas no coincidentes).
     */
    @PostMapping("/register")
    public String processRegistration(
            @Valid @ModelAttribute("userRegisterRequestDto") UserRegisterRequestDto registerDto,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "auth/register";
        }

        try {
            userService.registerUser(registerDto);
            redirectAttributes.addFlashAttribute("successMessage",
                    "¡Registro exitoso! Ya puedes iniciar sesión con tu cuenta.");
            return "redirect:/login";
        } catch (BadRequestException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "auth/register";
        }
    }
}