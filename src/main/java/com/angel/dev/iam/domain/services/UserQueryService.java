package com.angel.dev.iam.domain.services;

import com.angel.dev.iam.domain.model.queries.GetAllUsersQuery;
import com.angel.dev.iam.domain.model.queries.GetUserByIdQuery;
import com.angel.dev.iam.domain.model.queries.GetUserByUsernameQuery;
import org.apache.catalina.User;

import java.util.List;
import java.util.Optional;

public interface UserQueryService {
    List<User> handle(GetAllUsersQuery query);
    Optional<User> handle(GetUserByIdQuery query);
    Optional<User> handle(GetUserByUsernameQuery query);
}