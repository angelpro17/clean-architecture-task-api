package com.angel.dev.Task.domain.services;

import java.util.List;
import java.util.Optional;

import com.angel.dev.Task.domain.model.aggregates.Task;
import com.angel.dev.Task.domain.model.queries.GetAllTaskQuery;
import com.angel.dev.Task.domain.model.queries.GetTaskByEmailQuery;
import com.angel.dev.Task.domain.model.queries.GetTaskByIdQuery;

public interface TaskQueryService {
    List<Task> handle(GetAllTaskQuery query);

    Optional<Task> handle(GetTaskByEmailQuery query);

    Optional<Task> handle(GetTaskByIdQuery query);
}
