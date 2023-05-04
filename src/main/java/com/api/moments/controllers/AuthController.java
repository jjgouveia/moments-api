package com.api.moments.controllers;

import com.api.moments.services.auth.AuthRequest;
import com.api.moments.services.auth.AuthResponse;
import com.api.moments.services.auth.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")

public class AuthController {

  @Autowired
  private IAuthService authService;

  @PostMapping
  public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
    System.out.println("AuthController.login" + authRequest);

    return ResponseEntity.status(HttpStatus.OK).body(authService.authenticate(authRequest));
  }
}
