package com.angel.dev.Task.application.internal.queryservices;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.angel.dev.Task.domain.model.aggregates.Task;
import com.angel.dev.Task.domain.model.queries.GetAllTaskQuery;
import com.angel.dev.Task.domain.model.queries.GetTaskByEmailQuery;
import com.angel.dev.Task.domain.model.queries.GetTaskByIdQuery;
import com.angel.dev.Task.domain.model.repositories.TaskRepository;
import com.angel.dev.Task.domain.services.TaskQueryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TaskQueryServiceImpl implements TaskQueryService {
    private final TaskRepository taskRepository;

    public TaskQueryServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> handle(GetAllTaskQuery query) {
        log.debug("Fetching all tasks");
        List<Task> tasks = taskRepository.findAll();
        log.debug("Found {} tasks", tasks.size());
        return tasks;
    }

    @Override
    public Optional<Task> handle(GetTaskByEmailQuery query) {
        log.debug("Searching task by email: {}", query.emailAddress().address());
        Optional<Task> task = taskRepository.findByEmail(query.emailAddress());
        log.debug("Task found by email {}: {}", query.emailAddress().address(), task.isPresent());
        return task;
    }

    @Override
    public Optional<Task> handle(GetTaskByIdQuery query) {
        log.debug("Searching task by ID: {}", query.taskId());
        Optional<Task> task = taskRepository.findById(query.taskId());
        log.debug("Task found by ID {}: {}", query.taskId(), task.isPresent());
        return task;
    }

}
