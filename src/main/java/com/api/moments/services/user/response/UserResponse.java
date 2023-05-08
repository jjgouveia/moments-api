package com.api.moments.services.user.response;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class UserResponse {
  private UUID id;
  private String username;
  private String email;
  private String role;
  private List<UUID> followers;
  private List<UUID> following;

}
