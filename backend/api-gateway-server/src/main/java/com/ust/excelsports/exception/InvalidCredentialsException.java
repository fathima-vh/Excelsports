package com.ust.excelsports.exception;

public class InvalidCredentialsException extends RuntimeException{

    public InvalidCredentialsException(String msg){
        super(msg);
    }
}
