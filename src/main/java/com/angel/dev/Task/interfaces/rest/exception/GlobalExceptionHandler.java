package com.angel.dev.Task.interfaces.rest.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.angel.dev.Task.domain.exceptions.InvalidEmailFormatException;
import com.angel.dev.Task.domain.exceptions.TaskAlreadyExistsException;
import com.angel.dev.Task.domain.exceptions.TaskNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

        /**
         * Maneja TaskAlreadyExistsException.
         * Se lanza cuando se intenta crear una tarea con un email que ya existe.
         * 
         * @param ex      La excepción capturada
         * @param request La petición HTTP que causó el error
         * @return ResponseEntity con código 409 CONFLICT
         */
        @ExceptionHandler(TaskAlreadyExistsException.class)
        public ResponseEntity<ErrorResponse> handleTaskAlreadyExists(
                        TaskAlreadyExistsException ex,
                        HttpServletRequest request) {

                log.warn("Task already exists: {} at {}", ex.getMessage(), request.getRequestURI());

                ErrorResponse error = new ErrorResponse(
                                LocalDateTime.now(),
                                HttpStatus.CONFLICT.value(),
                                "Conflict",
                                ex.getMessage(),
                                request.getRequestURI(),
                                null);

                return ResponseEntity
                                .status(HttpStatus.CONFLICT)
                                .body(error);
        }

        /**
         * Maneja TaskNotFoundException.
         * Se lanza cuando no se encuentra una tarea por su ID o email.
         * 
         * @param ex      La excepción capturada
         * @param request La petición HTTP que causó el error
         * @return ResponseEntity con código 404 NOT FOUND
         */
        @ExceptionHandler(TaskNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleTaskNotFound(
                        TaskNotFoundException ex,
                        HttpServletRequest request) {

                log.info("Task not found: {} at {}", ex.getMessage(), request.getRequestURI());

                ErrorResponse error = new ErrorResponse(
                                LocalDateTime.now(),
                                HttpStatus.NOT_FOUND.value(),
                                "Not Found",
                                ex.getMessage(),
                                request.getRequestURI(),
                                null);

                return ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .body(error);
        }

        /**
         * Maneja InvalidEmailFormatException.
         * Se lanza cuando el formato del email es inválido.
         * 
         * @param ex      La excepción capturada
         * @param request La petición HTTP que causó el error
         * @return ResponseEntity con código 400 BAD REQUEST
         */
        @ExceptionHandler(InvalidEmailFormatException.class)
        public ResponseEntity<ErrorResponse> handleInvalidEmailFormat(
                        InvalidEmailFormatException ex,
                        HttpServletRequest request) {

                log.warn("Invalid email format: {} at {}", ex.getMessage(), request.getRequestURI());

                ErrorResponse error = new ErrorResponse(
                                LocalDateTime.now(),
                                HttpStatus.BAD_REQUEST.value(),
                                "Bad Request",
                                ex.getMessage(),
                                request.getRequestURI(),
                                null);

                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .body(error);
        }

        /**
         * Maneja errores de validación de Bean Validation (@Valid).
         * Se lanza cuando los datos de entrada no cumplen las validaciones.
         * 
         * Ejemplo: @NotBlank, @Email, @Size, etc.
         * 
         * @param ex      La excepción de validación
         * @param request La petición HTTP que causó el error
         * @return ResponseEntity con código 400 BAD REQUEST y lista de errores
         */
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorResponse> handleValidationErrors(
                        MethodArgumentNotValidException ex,
                        HttpServletRequest request) {

                // Extrae todos los errores de validación
                List<String> errors = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                                .collect(Collectors.toList());

                log.warn("Validation failed at {}: {}", request.getRequestURI(), errors);

                ErrorResponse error = new ErrorResponse(
                                LocalDateTime.now(),
                                HttpStatus.BAD_REQUEST.value(),
                                "Validation Failed",
                                "Invalid input data",
                                request.getRequestURI(),
                                errors);

                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .body(error);
        }

        /**
         * Maneja cualquier excepción no capturada por los handlers específicos.
         * 
         * @param ex      La excepción genérica
         * @param request La petición HTTP que causó el error
         * @return ResponseEntity con código 500 INTERNAL SERVER ERROR
         */
        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponse> handleGenericException(
                        Exception ex,
                        HttpServletRequest request) {

                log.error("Unexpected error at {}: {} - {}",
                                request.getRequestURI(),
                                ex.getClass().getSimpleName(),
                                ex.getMessage(),
                                ex);

                ErrorResponse error = new ErrorResponse(
                                LocalDateTime.now(),
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                "Internal Server Error",
                                "An unexpected error occurred. Please try again later.",
                                request.getRequestURI(),
                                null);

                return ResponseEntity
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(error);
        }
}
