package com.flightScheduler.app.exceptions;

public class GateNotAvailableAtThisTimeException extends RuntimeException {
    public GateNotAvailableAtThisTimeException(String message) {
        super(message);
    }
}
