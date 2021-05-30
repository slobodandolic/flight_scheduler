package com.flightScheduler.app.exceptions;

public class NoFreeGatesException extends RuntimeException {

    public NoFreeGatesException(String message) {
        super(message);
    }
}
