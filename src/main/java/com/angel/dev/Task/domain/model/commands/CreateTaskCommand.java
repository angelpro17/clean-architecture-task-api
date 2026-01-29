package com.angel.dev.Task.domain.model.commands;

import java.time.LocalDate;

public record CreateTaskCommand(
        String email,
        String title,
        String description,
        String status,
        String priority,
        LocalDate dueDate) {

}
