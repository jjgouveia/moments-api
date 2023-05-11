package com.api.moments.controllers;

import com.api.moments.services.auth.IAuthService;
import com.api.moments.services.auth.request.AuthRequest;
import com.api.moments.services.auth.response.AuthResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController extends BaseController {

  @Autowired
  private IAuthService authService;

  @PostMapping("/auth")
  public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest,
      HttpServletResponse response) {
    try {
      var auth = authService.authenticate(authRequest);
      response.setHeader("Authorization", "Bearer " + auth.getToken());
      return ResponseEntity.status(HttpStatus.OK).body(auth);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }
}
