package com.botanica.urbana.supportLayer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepción personalizada que se lanza cuando un usuario intenta acceder
 * o modificar un recurso que no le pertenece o para el cual carece de permisos (HTTP 403 FORBIDDEN).
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class UnauthorizedAccessException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    /**
     * Constructor con mensaje explicativo sobre la restricción de acceso.
     *
     * @param message Mensaje detallado de la denegación de acceso.
     */
    public UnauthorizedAccessException(String message) {
        super(message);
    }

    /**
     * Constructor con mensaje y causa raíz de la excepción.
     *
     * @param message Mensaje detallado del error.
     * @param cause Causa original de la excepción.
     */
    public UnauthorizedAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
