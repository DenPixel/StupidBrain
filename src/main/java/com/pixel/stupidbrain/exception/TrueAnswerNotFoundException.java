package com.pixel.stupidbrain.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class TrueAnswerNotFoundException extends StupidBrainException{
    public TrueAnswerNotFoundException(UUID id) {
        super(HttpStatus.BAD_REQUEST, "Answer with id " + id + " not found");
    }
}
