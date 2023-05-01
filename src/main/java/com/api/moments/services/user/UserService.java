package com.api.moments.services.user;

import com.api.moments.persistence.entities.User;

import java.util.List;

public interface UserService {
  User create(CreateUserRequest createUserRequest);

  List<User> getAll();

  boolean existsByUsernameAndEmail(String username, String email);

  boolean existsByUsername(String username);

  boolean existsByEmail(String email);
}
