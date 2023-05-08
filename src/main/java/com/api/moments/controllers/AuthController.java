package com.api.moments.controllers;

import com.api.moments.services.auth.IAuthService;
import com.api.moments.services.auth.request.AuthRequest;
import com.api.moments.services.auth.response.AuthResponse;
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
    try {
      var response = authService.authenticate(authRequest);
      return ResponseEntity.status(HttpStatus.OK).body(response);
    } catch (Exception e) {
      if (e.getMessage().equals("Email or password is null"))
        System.out.println(e.getMessage());
      else if (e.getMessage().equals("User not found"))
        System.out.println(e.getMessage());
      else if (e.getMessage().equals("Invalid password"))
        System.out.println(e.getMessage());

      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }
}
