package com.djs.common.exception;


public class JobNotFoundException extends RuntimeException{

    public JobNotFoundException(String message){
        super(message);
    }
}
