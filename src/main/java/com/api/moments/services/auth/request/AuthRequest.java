package com.api.moments.services.auth.request;

import lombok.Data;

@Data
public class AuthRequest {
  private String email;
  private String password;
}
