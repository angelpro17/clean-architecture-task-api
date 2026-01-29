package com.angel.dev.Task.interfaces.rest.transform;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.angel.dev.Task.domain.model.aggregates.Task;
import com.angel.dev.Task.domain.model.commands.CreateTaskCommand;
import com.angel.dev.Task.domain.model.commands.UpdateTaskPriorityCommand;
import com.angel.dev.Task.domain.model.commands.UpdateTaskStatusCommand;
import com.angel.dev.Task.interfaces.rest.resources.CreateTaskResource;
import com.angel.dev.Task.interfaces.rest.resources.TaskResource;
import com.angel.dev.Task.interfaces.rest.resources.UpdateTaskPriorityResource;

@Component
public class TaskMapper {
    public TaskMapper() {
    }

    // REQUEST a COMMAND
    /**
     * 
     * @param resource // DTO recibido del cliente
     * @return // comando para la capa de dominio
     */
    public CreateTaskCommand toCommand(CreateTaskResource resource) {
        return new CreateTaskCommand(
                resource.email(),
                resource.title(),
                resource.description(),
                resource.status(),
                resource.priority(),
                resource.dueDate());
    }

    // ENTITY a RESPONSE
    /**
     * Convierte Task (entidad) a TaskResource (DTO de salida).
     * 
     * @param entity Entidad del dominio
     * @return Resource listo para serializar a JSON
     */
    public TaskResource toResource(Task entity) {
        return new TaskResource(
                entity.getId(),
                entity.getEmail().address(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getStatus().name(),
                entity.getPriority().name(),
                entity.getDueDate());
    }

    public UpdateTaskStatusCommand toUpdateTaskStatusCommand(UUID taskId, String newStatus) {
        return new UpdateTaskStatusCommand(
                taskId,
                newStatus);
    }

    public UpdateTaskPriorityCommand toUpdateTaskPriorityCommand(UUID taskId, String newPriority) {
        return new UpdateTaskPriorityCommand(
                taskId,
                newPriority);
    }
}
