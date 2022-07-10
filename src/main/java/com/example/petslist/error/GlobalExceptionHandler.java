package com.example.petslist.error;

import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({AuthenticationException.class, MissingCsrfTokenException.class, InvalidCsrfTokenException.class, SessionAuthenticationException.class})
    public ResponseEntity<?> handleAuthenticationException(RuntimeException ex) {
        return APIError.UNAUTHORIZED.withParam(ex.getLocalizedMessage()).toResponseEntity();
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<?> dataAccessExceptionHandler(DataAccessException ex) {
        return APIError.DATABASE_ACCESS_ERROR.withParam(ex.getLocalizedMessage()).toResponseEntity();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> ExceptionHandler(RuntimeException ex) {
        return APIError.ANOTHER_ERROR.withParam(ex.getLocalizedMessage()).toResponseEntity();
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<?> apiExceptionHandler(APIException ex) {
        return ex.getError().toResponseEntity();
    }

}
