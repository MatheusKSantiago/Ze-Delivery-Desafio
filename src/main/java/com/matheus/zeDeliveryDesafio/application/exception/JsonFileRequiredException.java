package com.matheus.zeDeliveryDesafio.application.exception;

public class JsonFileRequiredException extends RuntimeException{
    public JsonFileRequiredException() {
        super("JSON File Required");
    }
}
