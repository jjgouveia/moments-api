package com.api.moments.services.user;

public class InvalidFollowException extends RuntimeException {
    public InvalidFollowException(String message) {
        super(message);
    }
}
