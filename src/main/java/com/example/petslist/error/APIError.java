package com.example.petslist.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public enum APIError {
    PET_ALREADY_EXIST
            (101, HttpStatus.BAD_REQUEST, "Pet with name %s already exist"),
    PET_NOT_FOUND
            (102, HttpStatus.NOT_FOUND,"Pet with id %s not found"),
    PET_LIST_IS_EMPTY
            (103, HttpStatus.NOT_FOUND,"Pets for user %s not found"),
    PET_OWNER_CONFLICT
            (104, HttpStatus.FORBIDDEN,"Pet with id %d has another owner"),
    DATABASE_ACCESS_ERROR
            (301, HttpStatus.NOT_FOUND,"Database access error"),
    USER_ALREADY_EXIST
            (201, HttpStatus.BAD_REQUEST,"User %s already exist"),
    UNAUTHORIZED
            (222, HttpStatus.UNAUTHORIZED,"AUTH_ERROR: %s"),
    ANOTHER_ERROR
            (333, HttpStatus.BAD_REQUEST,"ERROR: %s")
    ;


    private final int code;
    private final HttpStatus status;
    private String error;

    APIError(int code, HttpStatus status, String message) {
        this.code = code;
        this.error = message;
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public ResponseEntity<?> toResponseEntity() {
        return ResponseEntity.status(status).body(this);
    }

    public APIError withParam(String param) {
        this.error = String.format(this.error, param);
        return this;
    }

    public APIError withParam(long param) {
        this.error = String.format(this.error, param);
        return this;
    }
}
