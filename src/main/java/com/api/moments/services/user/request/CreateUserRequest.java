package com.api.moments.services.user.request;

import lombok.Data;

@Data
public class CreateUserRequest {
  private String username;
  private String email;
  private String password;

  public CreateUserRequest(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }
}
