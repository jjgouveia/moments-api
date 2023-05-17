package com.api.moments.controllers;

import com.api.moments.persistence.entities.User;
import com.api.moments.services.user.UserService;
import com.api.moments.services.user.request.CreateUserRequest;
import com.api.moments.services.user.request.SimpleUpdateUserRequest;
import com.api.moments.services.user.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    @GetMapping("/user/all")
    public List<User> getAllUsers() {
        return this.userService.getAll();
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String username) {
        try {
            UserResponse user = this.userService.getUserByUsername(username);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/user/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        try {
            User user = this.userService.getUserById(id);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/user/new-user")
    public ResponseEntity<String> newUser(@RequestBody CreateUserRequest createUserRequest) {
        try {
            this.userService.create(createUserRequest);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PatchMapping("/user/update")
    public ResponseEntity<String> updateUser(@RequestBody SimpleUpdateUserRequest createUserRequest,
                                             @RequestHeader("Authorization") String token) {
        try {
            this.userService.updateSimpleUser(token, createUserRequest);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (Exception e) {
            if (e.getMessage().equals("User not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @PostMapping("/user/{username}/follow")
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

    @PostMapping("/user/{username}/unfollow")
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
