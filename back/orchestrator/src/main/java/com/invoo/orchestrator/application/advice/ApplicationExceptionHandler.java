package com.invoo.orchestrator.application.advice;

import com.invoo.orchestrator.application.exceptions.ClientConnectionException;
import com.invoo.orchestrator.application.exceptions.ClientException;
import com.invoo.orchestrator.application.exceptions.GenericException;
import com.invoo.orchestrator.application.exceptions.UserAlreadyExists;
import com.invoo.orchestrator.application.exceptions.UserNotConnected;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body( errors );
    }

    @ExceptionHandler(UserNotConnected.class)
    public ResponseEntity<?> handleUserNotConnected(UserNotConnected ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ClientConnectionException.class)
    public ProblemDetail handleException(ClientConnectionException ex) {
        return build( HttpStatus.SERVICE_UNAVAILABLE, ex, problem -> {
            problem.setTitle("Connection refused");
        });
    }

    @ExceptionHandler(ClientException.class)
    public ProblemDetail handleException(ClientException ex) {
        return build( HttpStatus.BAD_REQUEST, ex, problem -> {
            problem.setTitle("Error when client processing");
        });
    }

    @ExceptionHandler(UserAlreadyExists.class)
    public ResponseEntity<?> handleUserNotConnected(UserAlreadyExists ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<?> handleUserNotConnected(GenericException ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getHttpStatus());
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<?> handleUserNotConnected(DisabledException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }


    private ProblemDetail build(HttpStatus status, Exception ex, Consumer<ProblemDetail> consumer) {
        var problem = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        consumer.accept(problem);
        return problem;
    }

}
