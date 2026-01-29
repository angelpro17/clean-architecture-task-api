package com.angel.dev.Task.interfaces.acl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.angel.dev.Task.domain.model.commands.CreateTaskCommand;
import com.angel.dev.Task.domain.model.queries.GetTaskByEmailQuery;
import com.angel.dev.Task.domain.model.valueobjects.EmailAddress;
import com.angel.dev.Task.domain.services.TaskCommandService;
import com.angel.dev.Task.domain.services.TaskQueryService;

@Service
public class TasksContextFacade {
    private final TaskCommandService taskCommandService;
    private final TaskQueryService taskQueryService;

    public TasksContextFacade(TaskCommandService taskCommandService, TaskQueryService taskQueryService) {
        this.taskCommandService = taskCommandService;
        this.taskQueryService = taskQueryService;
    }

    public UUID createTask(String email, String title, String description, String status) {
        var createTaskCommand = new CreateTaskCommand(email, title, description, status, null, null);
        var task = taskCommandService.handle(createTaskCommand);
        if (task.isEmpty())
            return null;
        return task.get().getId();

    }

    public UUID fetchTaskIdByEmail(String email) {
        var getTaskByEmailQuery = new GetTaskByEmailQuery(new EmailAddress(email));
        var task = taskQueryService.handle(getTaskByEmailQuery);
        if (task.isEmpty())
            return null;
        return task.get().getId();
    }

}
