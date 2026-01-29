package com.angel.dev.Task.domain.services;

import java.util.Optional;

import com.angel.dev.Task.domain.model.aggregates.Task;
import com.angel.dev.Task.domain.model.commands.CreateTaskCommand;
import com.angel.dev.Task.domain.model.commands.UpdateTaskPriorityCommand;
import com.angel.dev.Task.domain.model.commands.UpdateTaskStatusCommand;

public interface TaskCommandService {
    Optional<Task> handle(CreateTaskCommand command);

    Optional<Task> handle(UpdateTaskStatusCommand command);

    Optional<Task> handle(UpdateTaskPriorityCommand command);
}
