package com.pixel.stupidbrain.exception;

import org.springframework.http.HttpStatus;

public class LoginAlreadyExistsException extends StupidBrainException{
    public LoginAlreadyExistsException(String login) {
        super(HttpStatus.BAD_REQUEST, "Login " + login + " already exists");
    }
}
