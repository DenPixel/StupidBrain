package com.pixel.stupidbrain.exception;

import com.pixel.stupidbrain.entity.Question;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class QuestionNotFoundException extends StupidBrainException {
    public QuestionNotFoundException(UUID id) {
        super(HttpStatus.BAD_REQUEST, "Question with id " + id + " not found" );
    }
}
