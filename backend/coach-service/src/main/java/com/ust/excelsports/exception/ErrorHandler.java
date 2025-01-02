package com.ust.excelsports.exception;

import com.ust.excelsports.dto.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(CoachNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleCoachNotFoundException(CoachNotFoundException ex, HttpServletRequest request){

        ErrorResponseDto body = new ErrorResponseDto();
        body.setStatus(HttpStatus.NOT_FOUND.value());
        body.setMessage(ex.getMessage());
        body.setTimeStamp(System.currentTimeMillis());
        body.setPath(request.getRequestURI());
        body.setError("Coach Not Found");

        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(body);
    }


    @ExceptionHandler(DuplicateCoachException.class)
    public ResponseEntity<ErrorResponseDto> handleDuplicateCoachException(DuplicateCoachException ex, HttpServletRequest request){

        ErrorResponseDto body = new ErrorResponseDto();
        body.setStatus(HttpStatus.CONFLICT.value());
        body.setMessage(ex.getMessage());
        body.setTimeStamp(System.currentTimeMillis());
        body.setPath(request.getRequestURI());
        body.setError("Coach Already Exists");

        return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(body);
    }
}
