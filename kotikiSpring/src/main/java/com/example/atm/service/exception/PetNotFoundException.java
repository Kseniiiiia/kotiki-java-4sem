package com.example.atm.service.exception;

public class PetNotFoundException extends RuntimeException {
    public PetNotFoundException(Long id) {
        super("Pet not found with id: " + id);;
    }
}
