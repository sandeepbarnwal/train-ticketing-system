package com.cloudbees.tms.common;

import com.cloudbees.tms.exception.SeatNotAvailableException;
import com.cloudbees.tms.exception.TicketNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleValidationExceptions(Exception ex) {

         if (ex instanceof SeatNotAvailableException | ex instanceof TicketNotFoundException) {
            new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.CONFLICT);
        } else
         if (ex instanceof RuntimeException) {
             new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
         }
        return new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }
}
