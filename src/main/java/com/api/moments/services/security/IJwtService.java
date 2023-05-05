package com.api.moments.services.security;

import com.api.moments.util.InvalidTokenException;

import java.util.UUID;

public interface IJwtService {
  String generateToken(UUID userId);

  boolean isValidToken(String token) throws InvalidTokenException;

  UUID getUserId(String token);
}
