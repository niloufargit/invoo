package com.invoo.orchestrator.application.exceptions;

import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

public class ApplicationExceptions {

    public static <T> Mono<T> connectionException(String client){
        return Mono.error(new ClientConnectionException(client));
    }

    public static <T> Throwable clientException(String client){
        return new ClientException(client);
    }

    public static <T> void userNotConnected(){
        throw new UserNotConnected();
    }

    public static <T> void UserAlreadyExists(){
        throw new UserAlreadyExists();
    }

    public static <T> void genericException(String message, HttpStatus httpStatus){
        throw new GenericException(message, httpStatus);
    }

}
