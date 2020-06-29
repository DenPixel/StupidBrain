package com.pixel.stupidbrain.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class UsersAnswerNotFoundException extends StupidBrainException {
    public UsersAnswerNotFoundException(UUID id) {
        super(HttpStatus.BAD_REQUEST, "Users answer with id " + id + " not found");
    }
}
