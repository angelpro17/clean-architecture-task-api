package com.angel.dev.Task.domain.exceptions;

/**
 * Excepción base para todas las excepciones del dominio.
 * Todas las excepciones personalizadas deben heredar de esta.
 */

public class DomainException extends RuntimeException {
    protected DomainException(String message) {
        super(message);
    }

    protected DomainException(String message, Throwable cause) {
        super(message, cause);
    }

}
