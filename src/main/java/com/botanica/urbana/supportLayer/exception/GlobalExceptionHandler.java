package com.botanica.urbana.supportLayer.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Manejador global de excepciones para la aplicación web.
 * Intercepta los errores lanzados en los controladores o servicios y los redirige
 * a vistas HTML amigables de Thymeleaf cargando información útil sobre la falla.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    
    /**
     * Captura la excepción de recurso no encontrado (HTTP 404).
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleResourceNotFoundException(ResourceNotFoundException ex, Model model) {
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("error", "Recurso no encontrado");
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("timestamp", LocalDateTime.now());
        return "error/404";
    }

    /**
     * Captura excepciones de solicitudes incorrectas o reglas de negocio violadas (HTTP 400).
     */
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBadRequestException(BadRequestException ex, Model model) {
        model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
        model.addAttribute("error", "Solicitud incorrecta");
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("timestamp", LocalDateTime.now());
        return "error/400";
    }

    /**
     * Captura excepciones de acceso denegado o recursos ajenos al usuario (HTTP 403).
     */
    @ExceptionHandler(UnauthorizedAccessException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleUnauthorizedAccessException(UnauthorizedAccessException ex, Model model) {
        model.addAttribute("status", HttpStatus.FORBIDDEN.value());
        model.addAttribute("error", "Acceso denegado");
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("timestamp", LocalDateTime.now());
        return "error/403";
    }

    /**
     * Captura cualquier otra excepción inesperada no controlada en el sistema (HTTP 500).
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGlobalException(Exception ex, Model model) {
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        model.addAttribute("error", "Error interno del servidor");
        model.addAttribute("message", "Ocurrió un error inesperado en el sistema. Por favor, inténtelo de nuevo más tarde.");
        model.addAttribute("timestamp", LocalDateTime.now());
        return "error/500";
    }
}
