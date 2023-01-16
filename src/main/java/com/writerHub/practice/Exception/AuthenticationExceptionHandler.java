package com.writerHub.practice.Exception;

import com.writerHub.practice.Exception.AuthenticationExceptions.AuthorNotFound;
import com.writerHub.practice.models.AuthenticationError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import static com.writerHub.practice.Exception.AuthenticationExceptions.*;

@ControllerAdvice
public class AuthenticationExceptionHandler {

    public ResponseEntity<AuthenticationError> handler(AuthorNotFound ex){
        AuthenticationError error = new AuthenticationError();
        error.setStatusCode(HttpStatus.UNAUTHORIZED);
        error.setMessage(ex.getMessage());
        return  new ResponseEntity<>(error,error.getStatusCode());
    }

    public ResponseEntity<AuthenticationError> handler(AuthorExists ex){
        AuthenticationError error = new AuthenticationError();
        error.setStatusCode(HttpStatus.BAD_REQUEST);
        error.setMessage(ex.getMessage());
        return  new ResponseEntity<>(error,error.getStatusCode());
    }

    public ResponseEntity<AuthenticationError> handler(AuthorizationHeaderMissing ex){
        AuthenticationError error = new AuthenticationError();
        error.setStatusCode(HttpStatus.UNAUTHORIZED);
        error.setMessage(ex.getMessage());
        return  new ResponseEntity<>(error,error.getStatusCode());
    }
}
