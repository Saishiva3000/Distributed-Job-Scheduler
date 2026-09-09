package com.djs.common.advice;

import com.djs.common.exception.CompletedJobModificationException;
import com.djs.common.exception.JobNotFoundException;
import com.djs.common.exception.RunningJobModificationException;
import com.djs.common.advice.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(JobNotFoundException.class)
    public ResponseEntity<ExceptionResponse> JobNotFoundException(JobNotFoundException payLoad){
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(payLoad.getMessage());
        response.setDateTime(LocalDateTime.now());
        response.setStatus(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RunningJobModificationException.class)
    public ResponseEntity<ExceptionResponse> JobRunningException(RunningJobModificationException payLoad){
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(payLoad.getMessage());
        response.setDateTime(LocalDateTime.now());
        response.setStatus(HttpStatus.CONFLICT);

        return new ResponseEntity<>(response,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CompletedJobModificationException.class)
    public ResponseEntity<ExceptionResponse> CompletedJobModificationException(CompletedJobModificationException payLoad){
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(payLoad.getMessage());
        response.setDateTime(LocalDateTime.now());
        response.setStatus(HttpStatus.CONFLICT);

        return new ResponseEntity<>(response,HttpStatus.CONFLICT);
    }
}
