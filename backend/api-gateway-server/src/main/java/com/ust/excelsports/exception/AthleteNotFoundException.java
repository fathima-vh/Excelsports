package com.ust.excelsports.exception;

public class AthleteNotFoundException extends RuntimeException{
    public AthleteNotFoundException(String message) {
        super(message);
    }
}
