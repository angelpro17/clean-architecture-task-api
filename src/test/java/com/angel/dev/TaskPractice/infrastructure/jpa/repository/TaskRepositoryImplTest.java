package com.angel.dev.TaskPractice.infrastructure.jpa.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.angel.dev.Task.application.internal.commandservices.TaskCommandServiceImpl;
import com.angel.dev.Task.domain.exceptions.TaskAlreadyExistsException;
import com.angel.dev.Task.domain.model.aggregates.Task;
import com.angel.dev.Task.domain.model.commands.CreateTaskCommand;
import com.angel.dev.Task.domain.model.repositories.TaskRepository;
import com.angel.dev.Task.domain.model.valueobjects.EmailAddress;

@ExtendWith(MockitoExtension.class)
class TaskRepositoryImplTest {
    @Mock
    private TaskRepository taskRepository;
    @InjectMocks
    private TaskCommandServiceImpl taskCommandService;

    private CreateTaskCommand validCommand;

    @BeforeEach
    void setUp() {
        // se ejecuta antes de cada test
        // preparamos datos comunes
        validCommand = new CreateTaskCommand(
                "test@example.com",
                "Test Task",
                "This is a test task",
                "PENDING",
                "MEDIUM",
                LocalDate.of(2024, 12, 31));
    }

    @Test
    @DisplayName("Should create task successfully")
    void shouldCreateTaskSuccessfully() {
        // ARRANGE
        // Configuramos el mock para simular el comportamiento esperado
        when(taskRepository.existsByEmail(any(EmailAddress.class)))
                .thenReturn(false); // Simula que el email no existe

        when(taskRepository.save(any(Task.class)))
                .thenAnswer(invocation -> invocation.getArgument(0)); // Devuelve el mismo objeto que se guarda

        // ACT
        Optional<Task> result = taskCommandService.handle(validCommand);
        assertTrue(result.isPresent(), "El resultado debería contener una tarea");
        Task task = result.get();
        assertEquals("Test Task", task.getTitle());
        assertEquals("This is a test task", task.getDescription());
        // Verificamos que se llamaron los métodos del repositorio
        verify(taskRepository, times(1)).existsByEmail(any(EmailAddress.class));
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    @DisplayName("Should throw exception when creating task with existing email")
    void shouldThrowExceptionWhenCreatingTaskWithExistingEmail() {
        when(taskRepository.existsByEmail(any(EmailAddress.class)))
                .thenReturn(true); // Simula que el email ya existe
        // ACT & ASSERT
        TaskAlreadyExistsException exception = assertThrows(
                TaskAlreadyExistsException.class,
                () -> taskCommandService.handle(validCommand));
        assertTrue(exception.getMessage().contains("already exists"));
        // Verificamos que se llamó al método existsByEmail pero no al save
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    @DisplayName("Debería crear EmailAddress correctamente desde el comando")
    void shouldCreateEmailAddressFromCommand() {
        // ARRANGE
        when(taskRepository.existsByEmail(any(EmailAddress.class)))
                .thenReturn(false);
        when(taskRepository.save(any(Task.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        // ACT
        Optional<Task> result = taskCommandService.handle(validCommand);
        // ASSERT
        assertTrue(result.isPresent());
        assertNotNull(result.get().getEmail());
        assertEquals("test@example.com", result.get().getEmail().address());
    }
}
