package com.api.moments.controllers;

import com.api.moments.persistence.entities.User;
import com.api.moments.services.user.CreateUserRequest;
import com.api.moments.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("moments/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return this.userService.getAll();
    }

    @PostMapping("/new")
    public ResponseEntity<String> newUser(@RequestBody CreateUserRequest createUserRequest) {
        if (this.userService.existsByUsernameAndEmail(createUserRequest.getUsername(),
                createUserRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username or email already exists");
        }

        var response = this.userService.create(createUserRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
    }

    @PostMapping("{username}/follow")
    public ResponseEntity<String> followUser(@PathVariable String username,
                                             @RequestHeader("Authorization") String token) {
        try {
            this.userService.addFollow(token, username);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (Exception e) {
            if (e.getMessage().equals("User not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("{username}/unfollow")
    public ResponseEntity<String> unfollowUser(@PathVariable String username,
                                               @RequestHeader("Authorization") String token) {
        try {
            this.userService.removeFollow(token, username);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (Exception e) {
            if (e.getMessage().equals("User not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
