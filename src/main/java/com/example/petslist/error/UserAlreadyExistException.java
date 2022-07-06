package com.example.petslist.error;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String s) {
        super("There is an account with that user name: " + s);
    }
}
