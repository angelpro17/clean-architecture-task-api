package com.angel.dev.Task.infrastructure.persistence.jpa.repository;

import org.springframework.stereotype.Repository;

import com.angel.dev.Task.domain.model.aggregates.Task;
import com.angel.dev.Task.domain.model.repositories.TaskRepository;
import com.angel.dev.Task.domain.model.valueobjects.EmailAddress;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

    private final JpaTaskRepository jpaTaskRepository;

    public TaskRepositoryImpl(JpaTaskRepository jpaTaskRepository) {
        this.jpaTaskRepository = jpaTaskRepository;
    }

    @Override
    public Task save(Task task) {
        return jpaTaskRepository.save(task);
    }

    @Override
    public Optional<Task> findById(UUID id) {
        return jpaTaskRepository.findById(id);
    }

    @Override
    public Optional<Task> findByEmail(EmailAddress emailAddress) {
        return jpaTaskRepository.findByEmail(emailAddress);
    }

    @Override
    public List<Task> findAll() {
        return jpaTaskRepository.findAll();
    }

    @Override
    public boolean existsByEmail(EmailAddress emailAddress) {
        return jpaTaskRepository.existsByEmail(emailAddress);
    }

    @Override
    public void deleteById(UUID id) {
        jpaTaskRepository.deleteById(id);
    }
}
