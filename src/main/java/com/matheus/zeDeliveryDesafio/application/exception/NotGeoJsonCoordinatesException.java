package com.matheus.zeDeliveryDesafio.application.exception;

public class NotGeoJsonCoordinatesException extends RuntimeException{
    public NotGeoJsonCoordinatesException() {
        super("Coordinates do not follow the GeoJSON format.");
    }
}
