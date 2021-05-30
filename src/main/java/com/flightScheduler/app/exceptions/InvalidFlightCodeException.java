package com.flightScheduler.app.exceptions;

public class InvalidFlightCodeException extends RuntimeException {

    public InvalidFlightCodeException(String message) {
        super(message);
    }
}
