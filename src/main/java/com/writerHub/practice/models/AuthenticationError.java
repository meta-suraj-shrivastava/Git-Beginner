package com.writerHub.practice.models;

import org.springframework.http.HttpStatus;

public class AuthenticationError {
    private HttpStatus statusCode;
    private String message;

    public AuthenticationError(){}

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
