package com.angel.dev.Task.infrastructure.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.angel.dev.Task.domain.model.aggregates.Task;
import com.angel.dev.Task.domain.model.valueobjects.EmailAddress;

import java.util.Optional;
import java.util.UUID;

public interface JpaTaskRepository extends JpaRepository<Task, UUID> {

    Optional<Task> findByEmail(EmailAddress email);

    boolean existsByEmail(EmailAddress email);

}
