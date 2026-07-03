package com.shiva.jobscheduler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class GlobalExceptionHandler {

    public ResponseEntity<ExceptionResponse> JobNotFoundException(JobNotFoundException payLoad){
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(payLoad.getMessage());
        response.setDateTime(LocalDateTime.now());
        response.setStatus(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }
}
