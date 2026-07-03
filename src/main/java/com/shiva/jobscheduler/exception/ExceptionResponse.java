package com.shiva.jobscheduler.exception;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class ExceptionResponse {

    private String message;
    private LocalDateTime dateTime;
    private HttpStatus status;
}
