package com.pixel.stupidbrain.exception;

import org.springframework.http.HttpStatus;

public class FieldIsEmptyException extends StupidBrainException{
    public FieldIsEmptyException(String name) {
        super(HttpStatus.BAD_REQUEST, "Field \"" + name + "\" is empty");
    }
}
