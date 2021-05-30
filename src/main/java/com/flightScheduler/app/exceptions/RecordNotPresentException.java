package com.flightScheduler.app.exceptions;

public class RecordNotPresentException extends RuntimeException {
    public RecordNotPresentException(String message) {
        super(message);
    }
}
