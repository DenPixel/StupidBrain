package com.pixel.stupidbrain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class StupidBrainException extends ResponseStatusException {

    public StupidBrainException(HttpStatus status) {
        super(status);
    }

    public StupidBrainException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public StupidBrainException(HttpStatus status, String reason, Throwable cause) {
        super(status, reason, cause);
    }


}
