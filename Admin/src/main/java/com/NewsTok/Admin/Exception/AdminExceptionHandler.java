package com.NewsTok.Admin.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AdminExceptionHandler {

    @ExceptionHandler({AdminNotFoundException.class,GeminiApiResultNotFoundException.class,ImageProcessingException.class})
    public ResponseEntity<AdminException> handleAdminNotFoundException(AdminNotFoundException adminNotFoundException){
        AdminException adminException = new AdminException(
                adminNotFoundException.getMessage(),
                adminNotFoundException.getCause(),
                HttpStatus.NOT_FOUND
        );
        return new ResponseEntity<>(adminException, HttpStatus.NOT_FOUND);
    }
}
