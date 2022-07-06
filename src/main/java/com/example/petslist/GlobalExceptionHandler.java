package com.example.petslist;

import com.example.petslist.error.PetNotFoundException;
import com.example.petslist.error.UserAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler({AuthenticationException.class, MissingCsrfTokenException.class, InvalidCsrfTokenException.class, SessionAuthenticationException.class})
    public ResponseEntity<?> handleAuthenticationException(RuntimeException ex) {
        return new ApiError(HttpStatus.UNAUTHORIZED, ex.getLocalizedMessage()).responseEntity();
    }

    @ResponseBody
    @ExceptionHandler(PetNotFoundException.class)
    public ResponseEntity<?> petNotFoundHandler(PetNotFoundException ex) {
        return new ApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage()).responseEntity();
    }

    @ResponseBody
    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<?> userAlreadyExistHandler(UserAlreadyExistException ex) {
        return new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage()).responseEntity();
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> ExceptionHandler(Exception ex) {
        return new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage()).responseEntity();
    }

    class ApiError {

        private final HttpStatus status;
        private final String message;

        public ApiError(HttpStatus status, String message) {
            super();
            this.status = status;
            this.message = message;
        }

        public ResponseEntity<?> responseEntity() {
            return ResponseEntity.status(status).body(this);
        }
    }
}
