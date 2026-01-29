package com.angel.dev.Task.domain.model.commands;

import java.util.UUID;

public record UpdateTaskPriorityCommand(
        UUID taskId,
        String newPriority) {

}
