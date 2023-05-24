package com.api.moments.services.user;

import com.api.moments.persistence.entities.User;
import com.api.moments.services.user.request.CreateUserRequest;
import com.api.moments.services.user.request.SimpleUpdateUserRequest;
import com.api.moments.services.user.request.UpdateUserRequest;
import com.api.moments.services.user.response.UserResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {
  User create(CreateUserRequest createUserRequest);

  List<User> getAll();

  boolean existsByUsernameAndEmail(String username, String email);

  boolean existsByUsername(String username);

  boolean existsByEmail(String email);

  User getUser(String email);

  UserResponse getUserByUsername(String username);

  User getUserById(UUID id);

  List<UserResponse> exploreUsersNotFollowed(String token);
  
  User updateUser(String token, UpdateUserRequest updateUserRequest);

  void updateSimpleUser(String token, SimpleUpdateUserRequest updateUserRequest);


  void updateUserMoments(UUID userId, UUID momentId);

  void addFollow(String token, String followId);

  void removeFollow(String token, String followId);

  boolean hasFollowed(String userId, String userIdToFollow);

}
