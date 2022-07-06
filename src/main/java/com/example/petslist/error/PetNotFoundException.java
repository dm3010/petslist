package com.example.petslist.error;

public class PetNotFoundException extends RuntimeException {
    public PetNotFoundException(Long id) {
        super("Could not find pet " + id);
    }
}
