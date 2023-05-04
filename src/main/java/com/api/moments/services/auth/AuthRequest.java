package com.api.moments.services.auth;

import lombok.Data;

@Data
public class AuthRequest {
  private String email;
  private String password;
}
