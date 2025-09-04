package com.invoo.orchestrator.application.exceptions;

public class UserAlreadyExists extends RuntimeException {

    public UserAlreadyExists() {
        super("User already exists");
    }
}
