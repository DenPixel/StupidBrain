package com.pixel.stupidbrain.exception;

import org.springframework.http.HttpStatus;

public class RequestIsEmptyException extends StupidBrainException {
    public RequestIsEmptyException() {
        super(HttpStatus.BAD_REQUEST, "Request is empty");
    }
}
