package com.angel.dev.Task.interfaces.rest.resources;

import java.time.LocalDate;

public record CreateTaskResource(
        String email,
        String title,
        String description,
        String status,
        String priority,
        LocalDate dueDate) {
}