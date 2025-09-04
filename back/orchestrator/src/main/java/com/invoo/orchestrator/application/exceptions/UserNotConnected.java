package com.invoo.orchestrator.application.exceptions;

public class UserNotConnected extends RuntimeException {

    public UserNotConnected() {
        super("User not connected");
    }
}
