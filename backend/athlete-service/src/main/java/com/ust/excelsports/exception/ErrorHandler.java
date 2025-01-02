package com.ust.excelsports.exception;

import com.ust.excelsports.dto.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {



    @ExceptionHandler(AthleteNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleAthleteNotFoundException(AthleteNotFoundException ex, HttpServletRequest request){

        ErrorResponseDto body = new ErrorResponseDto();
        body.setStatus(500);
        body.setMessage(ex.getMessage());
        body.setTimeStamp(System.currentTimeMillis());
        body.setPath(request.getRequestURI());
        body.setError("Internal Server Error");

        return ResponseEntity.status(500).body(body);
    }


    @ExceptionHandler(DuplicateAthleteException.class)
    public ResponseEntity<ErrorResponseDto> handleDuplicateAthleteException(DuplicateAthleteException ex, HttpServletRequest request){

        ErrorResponseDto body = new ErrorResponseDto();
        body.setStatus(500);
        body.setMessage(ex.getMessage());
        body.setTimeStamp(System.currentTimeMillis());
        body.setPath(request.getRequestURI());
        body.setError("Internal Server Error");

        return ResponseEntity.status(500).body(body);
    }

}
