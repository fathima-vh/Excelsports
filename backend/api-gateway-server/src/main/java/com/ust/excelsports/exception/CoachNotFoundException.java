package com.ust.excelsports.exception;

public class CoachNotFoundException extends RuntimeException{
    public CoachNotFoundException(String message) {
        super(message);
    }
}
