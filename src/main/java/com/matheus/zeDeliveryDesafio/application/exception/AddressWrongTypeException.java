package com.matheus.zeDeliveryDesafio.application.exception;

public class AddressWrongTypeException extends RuntimeException{
    public AddressWrongTypeException(String typeReceived) {
        super("Expected type: Point, received:" + typeReceived);
    }
}
