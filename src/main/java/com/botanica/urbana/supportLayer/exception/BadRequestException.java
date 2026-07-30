package com.botanica.urbana.supportLayer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepción personalizada que se lanza cuando una solicitud contiene datos
 * inválidos o viola las reglas de negocio del sistema (HTTP 400 BAD REQUEST).
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    /**
     * Constructor con mensaje explicativo de la regla de negocio violada.
     *
     * @param message Mensaje detallado del error.
     */
    public BadRequestException(String message) {
        super(message);
    }

    /**
     * Constructor con mensaje y causa raíz de la excepción.
     *
     * @param message Mensaje detallado del error.
     * @param cause Causa original del fallo.
     */
    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
