package com.angel.dev.Task.domain.exceptions;

public class InvalidEmailFormatException extends DomainException {
    public InvalidEmailFormatException(String email) {
        super("The email format is invalid: " + email);
    }

}
