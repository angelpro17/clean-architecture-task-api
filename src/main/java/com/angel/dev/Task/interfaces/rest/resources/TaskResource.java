package com.angel.dev.Task.interfaces.rest.resources;

import java.time.LocalDate;
import java.util.UUID;

public record TaskResource(
        UUID id,
        String email,
        String title,
        String description,
        String status,
        String priority,
        LocalDate dueDate) {
}