package com.api.moments.services.auth;

import com.api.moments.services.security.IJwtService;
import com.api.moments.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService {

  @Autowired
  private UserService userService;

  @Autowired
  private IJwtService jwtService;

  @Override
  public AuthResponse authenticate(AuthRequest authRequest) {
    var user = userService.getUser(authRequest.getEmail());

    if (user == null) {
      return null;
    }

    if (!user.getPassword().equals(authRequest.getPassword())) {
      return null;
    }



    var token = jwtService.generateToken(user.getId());

    var response = new AuthResponse();
    response.setUserId(user.getId());
    response.setToken(token);

    return response;
  }
}
