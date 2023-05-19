package com.api.moments.services.user.response;

import com.api.moments.persistence.entities.User;
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
  private List<UUID> moments;

  public UserResponse() {
  }

  public UserResponse(User user) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.email = user.getEmail();
    this.role = user.getRole();
    this.followers = user.getFollowers();
    this.following = user.getFollowing();
    this.moments = user.getMoments();
  }

}
