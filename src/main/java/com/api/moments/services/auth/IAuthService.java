package com.api.moments.services.auth;

import com.api.moments.services.auth.request.AuthRequest;
import com.api.moments.services.auth.response.AuthResponse;

public interface IAuthService {
  AuthResponse authenticate(AuthRequest authRequest);
}
