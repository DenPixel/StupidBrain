package com.pixel.stupidbrain.exception;

import org.springframework.http.HttpStatus;

public class PasswordMismatchException extends StupidBrainException{
    public PasswordMismatchException() {
        super(HttpStatus.BAD_REQUEST, "Password mismatch");
    }
}
