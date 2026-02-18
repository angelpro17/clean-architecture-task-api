package com.angel.dev.iam.domain.services;

import com.angel.dev.iam.domain.model.commands.SignInCommand;
import com.angel.dev.iam.domain.model.commands.SignUpCommand;
import org.apache.catalina.User;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Optional;

public interface UserCommandService {
    Optional<ImmutablePair<User, String>> handle(SignInCommand command);
    Optional<User> handle(SignUpCommand command);
}
