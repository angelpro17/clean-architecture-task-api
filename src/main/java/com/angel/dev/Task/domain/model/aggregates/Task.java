package com.angel.dev.Task.domain.model.aggregates;

import java.time.LocalDate;

import com.angel.dev.Task.domain.enums.Priority;
import com.angel.dev.Task.domain.enums.Status;
import com.angel.dev.Task.domain.model.commands.CreateTaskCommand;
import com.angel.dev.Task.domain.model.valueobjects.EmailAddress;
import com.angel.dev.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Entity
@Getter
public class Task extends AuditableAbstractAggregateRoot<Task> {

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "address", column = @Column(name = "email_address"))
    })
    private EmailAddress email;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;
    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private Priority priority;
    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    public Task(String title, String description, Status status, Priority priority, LocalDate dueDate) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    public Task(CreateTaskCommand command) {
        this.email = new EmailAddress(command.email());
        this.title = command.title();
        this.description = command.description();
        this.status = Status.valueOf(command.status());
        this.priority = Priority.valueOf(command.priority());
        this.dueDate = command.dueDate();
    }

    public Task() {
        // Default constructor for JPA
    }

    public void updateEmail(String email) {
        this.email = new EmailAddress(email);
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    public void updateStatus(String newStatus) {
        this.status = Status.valueOf(newStatus);
    }

    public void updatePriority(String newPriority) {
        this.priority = Priority.valueOf(newPriority);
    }
}
