package com.matheus.zeDeliveryDesafio.application.exception;

public class ParceiroNotFoundException extends RuntimeException{
    public ParceiroNotFoundException(long id) {
        super("Parceiro id: " + id + " not found");
    }
}
