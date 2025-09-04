package com.invoo.orchestrator.application.exceptions;

public class ClientException extends RuntimeException {

    public ClientException(String client) {
        super(client);
    }

}
