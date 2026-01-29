package com.angel.dev.TaskPractice.domain.model.aggregates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.angel.dev.Task.domain.enums.Priority;
import com.angel.dev.Task.domain.enums.Status;
import com.angel.dev.Task.domain.model.aggregates.Task;
import com.angel.dev.Task.domain.model.commands.CreateTaskCommand;

class TaskTest {

    @Test
    void shouldCreateTaskSuccessfully() {
        // Preparamos y creamos el comando con datos de prueba
        CreateTaskCommand command = new CreateTaskCommand(
                "anampa@gmail.com",
                "Apliaciones web",
                "Crear una aplicacion web con Spring Boot",
                "IN_PROGRESS",
                "HIGH",
                LocalDate.of(2024, 12, 31));

        // Actuamos
        Task task = new Task(command);
        // ASSERT (Verificamos)
        // verificamos que los atributos del Task se hayan asignado correctamente
        assertNotNull(task, "El objeto Task no debe ser nulo");
        assertEquals("Apliaciones web", task.getTitle());
        assertEquals(Status.IN_PROGRESS, task.getStatus());
    }

    @Test
    void shouldUpdateEmail() {
        Task task = new Task(
                "Apliaciones web",
                "Crear una aplicacion web con Spring Boot",
                Status.IN_PROGRESS,
                Priority.HIGH, // ← Cambiado de null a Priority.HIGH
                LocalDate.of(2024, 12, 31) // ← Cambiado de String a LocalDate
        );
        String newEmail = "newemail@example.com";
        task.updateEmail(newEmail);
        assertNotNull(task.getEmail());
        assertEquals(newEmail, task.getEmail().address());
    }

    @Test
    void shouldUpdateTitle() {
        // ARRANGE
        Task task = new Task(
                "Old Title",
                "Description",
                Status.PENDING,
                Priority.LOW,
                LocalDate.of(2026, 2, 1));
        String newTitle = "New Title";
        // ACT
        task.updateTitle(newTitle);
        // ASSERT
        assertEquals(newTitle, task.getTitle());
    }

    @Test
    void shouldUpdateDescription() {
        // ARRANGE
        Task task = new Task(
                "Title",
                "Old Description",
                Status.PENDING,
                Priority.LOW,
                LocalDate.of(2026, 2, 1));
        String newDescription = "New Description";
        // ACT
        task.updateDescription(newDescription);
        // ASSERT
        assertEquals(newDescription, task.getDescription());
    }

}
