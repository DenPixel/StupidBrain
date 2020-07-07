package com.pixel.stupidbrain.exception;

import org.springframework.http.HttpStatus;

public class UsernameAlreadyExistsException extends StupidBrainException {

    public UsernameAlreadyExistsException(String username) {
        super(HttpStatus.BAD_REQUEST, "Username " + username + " already exists");
    }
}
