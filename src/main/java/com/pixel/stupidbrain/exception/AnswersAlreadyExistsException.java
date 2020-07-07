package com.pixel.stupidbrain.exception;

import org.springframework.http.HttpStatus;

public class AnswersAlreadyExistsException extends StupidBrainException{
    public AnswersAlreadyExistsException(String answer) {
        super(HttpStatus.BAD_REQUEST, "Answer \"" + answer + "\" already exists");
    }
}
