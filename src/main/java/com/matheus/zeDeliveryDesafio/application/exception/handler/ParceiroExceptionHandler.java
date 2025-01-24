package com.matheus.zeDeliveryDesafio.application.exception.handler;

import com.matheus.zeDeliveryDesafio.application.exception.JsonFileRequiredException;
import com.matheus.zeDeliveryDesafio.application.exception.ParceiroNotFoundException;
import com.matheus.zeDeliveryDesafio.application.exception.PointOutOfCoverageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ParceiroExceptionHandler {

    @ExceptionHandler({PointOutOfCoverageException.class,ParceiroNotFoundException.class})
    public ResponseEntity<String> handlePointOutOfCoverageException(Exception ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    @ExceptionHandler(JsonFileRequiredException.class)
    public ResponseEntity<String> jsonFile(JsonFileRequiredException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
