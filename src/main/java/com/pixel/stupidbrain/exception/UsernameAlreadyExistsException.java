package com.pixel.stupidbrain.exception;

import org.springframework.http.HttpStatus;

public class UsernameAlreadyExistsException extends StupidBrainException {

    public UsernameAlreadyExistsException(String nickname) {
        super(HttpStatus.BAD_REQUEST, "Username " + nickname + " already exists");
    }
}
