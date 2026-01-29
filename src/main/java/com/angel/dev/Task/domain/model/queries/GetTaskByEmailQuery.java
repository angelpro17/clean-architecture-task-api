package com.angel.dev.Task.domain.model.queries;

import com.angel.dev.Task.domain.model.valueobjects.EmailAddress;

public record GetTaskByEmailQuery(
        EmailAddress emailAddress) {

}
