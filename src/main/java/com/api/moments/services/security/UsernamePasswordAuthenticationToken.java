package com.api.moments.services.security;

import com.api.moments.persistence.entities.User;

public class UsernamePasswordAuthenticationToken {
  private final User user;
  private final String token;

  public UsernamePasswordAuthenticationToken(User user, String token) {
    this.user = user;
    this.token = token;
  }

  public User getUser() {
    return user;
  }

  public String getToken() {
    return token;
  }
}
