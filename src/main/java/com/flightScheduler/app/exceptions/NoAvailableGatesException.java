package com.flightScheduler.app.exceptions;

public class NoAvailableGatesException extends RuntimeException {

    public NoAvailableGatesException(String message) {
        super(message);
    }
}
