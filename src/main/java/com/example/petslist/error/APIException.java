package com.example.petslist.error;

public class APIException extends RuntimeException {
    private final APIError error;

    public APIException(APIError error) {
        super(error.getError());
        this.error = error;
    }

    public APIError getError() {
        return error;
    }
}
