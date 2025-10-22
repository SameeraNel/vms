package com.airport.vms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidVisitStatusException extends RuntimeException {

    public InvalidVisitStatusException(String message) {
        super(message);
    }
}
