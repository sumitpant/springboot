package com.sumit.expnseTracker.commonResponse;

import org.springframework.http.HttpStatus;

public class JwtResponse {
    private String authToken;
    private HttpStatus status;
    private String userName;
    public  JwtResponse(String authToken , HttpStatus status,String userName){
        this.authToken=authToken;
        this.status=status;
        this.userName=userName;

    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


}
