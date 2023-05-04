package com.api.moments.services.auth;

public interface IAuthService {
  AuthResponse authenticate(AuthRequest authRequest);
}
