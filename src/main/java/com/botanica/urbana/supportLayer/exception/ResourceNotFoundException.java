package com.botanica.urbana.supportLayer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepción personalizada que se lanza cuando un recurso solicitado
 * no es encontrado en la base de datos (HTTP 404 NOT FOUND).
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    /**
     * Constructor con mensaje personalizado.
     *
     * @param message Mensaje descriptivo del error.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructor estructurado para formatear mensajes de recursos no encontrados.
     * Ejemplo de salida: "Producto no encontrado con id: '999'"
     *
     * @param resourceName Nombre de la entidad/recurso (ej. "Producto", "Usuario", "Categoría").
     * @param fieldName Campo por el cual se realizó la búsqueda (ej. "id", "email").
     * @param fieldValue Valor buscado que originó la falla.
     */
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s no encontrado con %s: '%s'", resourceName, fieldName, fieldValue));
    }
}
