package com.angel.dev.iam.domain.services;

import com.angel.dev.iam.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {
    void handle(SeedRolesCommand command);
}
