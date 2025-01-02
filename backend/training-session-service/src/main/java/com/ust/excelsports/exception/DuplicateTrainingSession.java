package com.ust.excelsports.exception;

public class DuplicateTrainingSession extends RuntimeException{
    public DuplicateTrainingSession(String msg){
        super(msg);
    }
}
