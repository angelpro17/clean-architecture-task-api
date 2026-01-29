package com.angel.dev.Task.application.internal.commandservices;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.angel.dev.Task.domain.exceptions.TaskAlreadyExistsException;
import com.angel.dev.Task.domain.model.aggregates.Task;
import com.angel.dev.Task.domain.model.commands.CreateTaskCommand;
import com.angel.dev.Task.domain.model.commands.UpdateTaskPriorityCommand;
import com.angel.dev.Task.domain.model.commands.UpdateTaskStatusCommand;
import com.angel.dev.Task.domain.model.repositories.TaskRepository;
import com.angel.dev.Task.domain.model.valueobjects.EmailAddress;
import com.angel.dev.Task.domain.services.TaskCommandService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TaskCommandServiceImpl implements TaskCommandService {

    private final TaskRepository taskRepository;

    public TaskCommandServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Optional<Task> handle(CreateTaskCommand command) {
        log.info("Attempting to create task for email: {}", command.email());

        var emailAddress = new EmailAddress(command.email());

        // Check if task already exists with this email
        if (taskRepository.existsByEmail(emailAddress)) {
            log.warn("Task creation failed: email {} already exists", command.email());
            throw new TaskAlreadyExistsException(command.email());
        }

        Task task = new Task(command);
        Task savedTask = taskRepository.save(task);

        log.info("Task created successfully with ID: {} for email: {}", savedTask.getId(), command.email());
        return Optional.of(savedTask);
    }

    @Override
    public Optional<Task> handle(UpdateTaskStatusCommand command) {
        var taskOptional = taskRepository.findById(command.taskId());
        if (taskOptional.isEmpty()) {
            log.warn("Task update failed: task with ID {} not found", command.taskId());
            return Optional.empty();
        }
        var task = taskOptional.get();
        task.updateStatus(command.newStatus());
        var updateTask = taskRepository.save(task);
        return Optional.of(updateTask);
    }

    @Override
    public Optional<Task> handle(UpdateTaskPriorityCommand command) {
        var taskOptional = taskRepository.findById(command.taskId());
        if (taskOptional.isEmpty()) {
            log.warn("Task priority update failed: task with ID {} not found", command.taskId());
            return Optional.empty();
        }
        var task = taskOptional.get();
        task.updatePriority(command.newPriority());
        var updateTask = taskRepository.save(task);
        return Optional.of(updateTask);
    }

}
