package com.angel.dev.Task.interfaces.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.angel.dev.Task.domain.model.queries.GetTaskByEmailQuery;
import com.angel.dev.Task.domain.model.queries.GetTaskByIdQuery;
import com.angel.dev.Task.domain.model.valueobjects.EmailAddress;
import com.angel.dev.Task.domain.services.TaskCommandService;
import com.angel.dev.Task.domain.services.TaskQueryService;
import com.angel.dev.Task.interfaces.rest.resources.CreateTaskResource;
import com.angel.dev.Task.interfaces.rest.resources.TaskResource;
import com.angel.dev.Task.interfaces.rest.resources.UpdateTaskPriorityResource;
import com.angel.dev.Task.interfaces.rest.resources.UpdateTaskStatusResource;
import com.angel.dev.Task.interfaces.rest.transform.TaskMapper;

import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.UUID;

import org.hibernate.sql.Update;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Task API", description = "API for managing tasks")
public class TaskController {
    private final TaskQueryService taskQueryService;
    private final TaskCommandService taskCommandService;
    private final TaskMapper taskMapper;

    public TaskController(TaskQueryService taskQueryService, TaskCommandService taskCommandService,
            TaskMapper taskMapper) {
        this.taskQueryService = taskQueryService;
        this.taskCommandService = taskCommandService;
        this.taskMapper = taskMapper;
    }

    @PostMapping
    public ResponseEntity<TaskResource> createTask(@Valid @RequestBody CreateTaskResource resource) {
        log.info("POST /api/v1/tasks - Creating task for email: {}", resource.email());
        var createTaskCommand = taskMapper.toCommand(resource);
        var task = taskCommandService.handle(createTaskCommand);
        if (task.isEmpty()) {
            log.warn("Task creation failed for email: {}", resource.email());
            return ResponseEntity.badRequest().build();
        }
        var taskResource = taskMapper.toResource(task.get());
        log.info("Task created successfully with ID: {}", task.get().getId());
        return ResponseEntity.ok(taskResource);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResource> getTaskById(@PathVariable UUID taskId) {
        log.info("GET /api/v1/tasks/{} - Fetching task by ID", taskId);
        var getTaskByIdQuery = new GetTaskByIdQuery(taskId);
        var task = taskQueryService.handle(getTaskByIdQuery);
        if (task.isEmpty()) {
            log.info("Task not found with ID: {}", taskId);
            return ResponseEntity.notFound().build();
        }
        log.debug("Task found with ID: {}", taskId);
        var taskResource = taskMapper.toResource(task.get());
        return ResponseEntity.ok(taskResource);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<TaskResource> getTaskByEmail(@PathVariable String email) {
        log.info("GET /api/v1/tasks/email/{} - Fetching task by email", email);
        var emailAddress = new EmailAddress(email);
        var getTaskByEmailQuery = new GetTaskByEmailQuery(emailAddress);
        var task = taskQueryService.handle(getTaskByEmailQuery);
        if (task.isEmpty()) {
            log.info("Task not found with email: {}", email);
            return ResponseEntity.notFound().build();
        }
        log.debug("Task found with email: {}", email);
        var taskResource = taskMapper.toResource(task.get());
        return ResponseEntity.ok(taskResource);
    }

    @PatchMapping("/{taskId}/status")
    public ResponseEntity<TaskResource> updateTaskStatus(@PathVariable UUID taskId,
            @Valid @RequestBody UpdateTaskStatusResource resource) {
        log.info("PATCH /api/v1/tasks/{}/status - Updating task status", taskId);
        var updateTaskStatusCommand = taskMapper.toUpdateTaskStatusCommand(taskId, resource.status());
        log.info("Updating status to: {} for task ID: {}", resource.status(), taskId);
        var task = taskCommandService.handle(updateTaskStatusCommand);
        if (task.isEmpty()) {
            log.warn("Task status update failed for ID: {}", taskId);
            return ResponseEntity.notFound().build();
        }
        var taskResource = taskMapper.toResource(task.get());
        log.info("Task status updated successfully for ID: {}", taskId);
        return ResponseEntity.ok(taskResource);
    }

    @PatchMapping("/{taskId}/priority")
    public ResponseEntity<TaskResource> updateTaskPriority(@PathVariable UUID taskId,
            @Valid @RequestBody UpdateTaskPriorityResource resource) {
        log.info("PATCH /api/v1/tasks/{}/priority - Updating task priority", taskId);
        var updateTaskPriorityCommand = taskMapper.toUpdateTaskPriorityCommand(taskId, resource.priority());
        log.info("Updating priority to: {} for task ID: {}", resource.priority(), taskId);
        var task = taskCommandService.handle(updateTaskPriorityCommand);
        if (task.isEmpty()) {
            log.warn("Task priority update failed for ID: {}", taskId);
            return ResponseEntity.notFound().build();
        }
        var taskResource = taskMapper.toResource(task.get());
        log.info("Task priority updated successfully for ID: {}", taskId);
        return ResponseEntity.ok(taskResource);
    }
}
