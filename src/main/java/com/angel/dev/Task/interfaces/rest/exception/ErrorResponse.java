package com.angel.dev.Task.interfaces.rest.exception;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 
 * Esta clase se usa en GlobalExceptionHandler para retornar
 * respuestas de error estructuradas y consistentes.
 */
public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        List<String> details) {
    /**
     * Constructor para errores simples sin detalles adicionales.
     * 
     * @param status  Código de estado HTTP
     * @param error   Tipo de error ( "Bad Request", "Not Found")
     * @param message Mensaje descriptivo del error
     * @param path    Ruta del endpoint donde ocurrió el error
     */
    public ErrorResponse(int status, String error, String message, String path) {
        this(LocalDateTime.now(), status, error, message, path, null);
    }
}
