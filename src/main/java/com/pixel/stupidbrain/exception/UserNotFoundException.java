package com.pixel.stupidbrain.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class UserNotFoundException extends StupidBrainException{

    public UserNotFoundException(UUID id) {
        super(HttpStatus.BAD_REQUEST, "User with id " + id + " not found");
    }
}
