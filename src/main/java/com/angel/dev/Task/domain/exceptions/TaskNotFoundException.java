package com.angel.dev.Task.domain.exceptions;

public class TaskNotFoundException extends DomainException {
    public TaskNotFoundException(String taskId) {
        super("Task with ID " + taskId + " not found.");
    }
}
