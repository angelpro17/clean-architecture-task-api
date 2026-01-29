package com.angel.dev.Task.domain.model.commands;

import java.util.UUID;

public record UpdateTaskStatusCommand(
        UUID taskId,
        String newStatus) {
}
