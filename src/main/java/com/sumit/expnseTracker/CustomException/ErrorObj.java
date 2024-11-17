package com.sumit.expnseTracker.CustomException;

import org.springframework.http.HttpStatus;

public class ErrorObj {

    public String message;
    public HttpStatus statusCode;

    public ErrorObj(String message, HttpStatus statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
