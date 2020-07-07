package com.pixel.stupidbrain.exception;

import org.springframework.http.HttpStatus;

public class UsernameNotFoundException extends StupidBrainException {
    public UsernameNotFoundException(String username) {
        super(HttpStatus.BAD_REQUEST, "User with username " + username + " not found");
    }
}
