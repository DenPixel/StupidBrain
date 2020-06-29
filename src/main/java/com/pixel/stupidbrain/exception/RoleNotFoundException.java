package com.pixel.stupidbrain.exception;

import org.springframework.http.HttpStatus;

public class RoleNotFoundException extends StupidBrainException{
    public RoleNotFoundException(String role) {
        super(HttpStatus.BAD_REQUEST, "Role " + role + " not found");
    }
}
