package com.api.moments.services.auth.response;

import lombok.Data;

import java.util.UUID;

@Data
public class AuthResponse {
  private UUID userId;
  private String token;
}
