package com.sumit.expnseTracker.commonResponse;

import org.springframework.http.HttpStatus;

import java.util.List;

public class Response<T,D> {
   public T message;
   public HttpStatus status;
   public T responseCode;
   public D list=null;

    public Response(T message, T responseCode, HttpStatus status,D list) {
        this.message = message;
        this.responseCode = responseCode;
        this.status = status;
        this.list= list;
    }
}
