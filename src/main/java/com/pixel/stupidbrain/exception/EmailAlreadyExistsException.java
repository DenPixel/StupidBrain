package com.pixel.stupidbrain.exception;

import org.springframework.http.HttpStatus;

public class EmailAlreadyExistsException extends StupidBrainException {

    public EmailAlreadyExistsException(String email) {
        super(HttpStatus.BAD_REQUEST, "Email " + email + " already exists");
    }


}
