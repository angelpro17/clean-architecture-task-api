package com.angel.dev.Task.domain.model.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.angel.dev.Task.domain.model.aggregates.Task;
import com.angel.dev.Task.domain.model.valueobjects.EmailAddress;

public interface TaskRepository {

    Task save(Task task);

    Optional<Task> findById(UUID id);

    Optional<Task> findByEmail(EmailAddress emailAddress);

    List<Task> findAll();

    boolean existsByEmail(EmailAddress emailAddress);

    void deleteById(UUID id);

}
