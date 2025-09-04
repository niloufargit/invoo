package com.invoo.catalog.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class ApplicationExceptions extends RuntimeException {

    private HttpStatus httpStatus;

    public ApplicationExceptions(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public static void missingCompanyId(String message, HttpStatus httpStatus) {
        throw new ApplicationExceptions(message, httpStatus);
    }

    public static void exist(String message, HttpStatus httpStatus) {
        throw new ApplicationExceptions(message, httpStatus);
    }

    public static void processingFile(String message, HttpStatus httpStatus) {
        throw new ApplicationExceptions(message, httpStatus);
    }
}
