package com.matheus.zeDeliveryDesafio.application.exception;

public class CoverageAreaWrongTypeException extends RuntimeException{
    public CoverageAreaWrongTypeException(String typeReceived) {
        super("Expected type: MultiPolygon, received:" + typeReceived);
    }
}
