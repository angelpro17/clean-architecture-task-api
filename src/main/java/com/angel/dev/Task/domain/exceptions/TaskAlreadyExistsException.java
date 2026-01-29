package com.angel.dev.Task.domain.exceptions;

public class TaskAlreadyExistsException extends DomainException {
    public TaskAlreadyExistsException(String email) {
        super("Task with email " + email + " already exists.");
    }

}
