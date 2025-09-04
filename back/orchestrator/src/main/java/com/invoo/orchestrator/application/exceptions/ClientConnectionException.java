package com.invoo.orchestrator.application.exceptions;

public class ClientConnectionException extends RuntimeException {

    private static final String MESSAGE = "%s service is not available";

    public ClientConnectionException(String client) {
        super(MESSAGE.formatted(client.toUpperCase()));
    }

}
