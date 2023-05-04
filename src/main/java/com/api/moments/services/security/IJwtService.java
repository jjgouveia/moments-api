package com.api.moments.services.security;

import java.util.UUID;

public interface IJwtService {
  String generateToken(UUID userId);
}
