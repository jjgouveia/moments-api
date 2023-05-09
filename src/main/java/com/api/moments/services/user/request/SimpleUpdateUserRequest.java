package com.api.moments.services.user.request;

import lombok.Data;

import java.util.UUID;

@Data
public class SimpleUpdateUserRequest {
    private UUID id;
    private String username;
    private String email;
    private String password;

    public SimpleUpdateUserRequest() {
        this.username = this.getUsername();
        this.email = this.getEmail();
        this.password = this.getPassword();
    }
}
