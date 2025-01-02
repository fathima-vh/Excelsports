package com.ust.excelsports.exception;

import com.ust.excelsports.dto.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(DuplicateTrainingSession.class)
    public ResponseEntity<ErrorResponseDto> handleCoachNotFoundException(DuplicateTrainingSession ex, HttpServletRequest request){

        ErrorResponseDto body = new ErrorResponseDto();
        body.setStatus(HttpStatus.CONFLICT.value());
        body.setMessage(ex.getMessage());
        body.setTimeStamp(System.currentTimeMillis());
        body.setPath(request.getRequestURI());
        body.setError("Training session already exists!!");

        return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(body);
    }


}
