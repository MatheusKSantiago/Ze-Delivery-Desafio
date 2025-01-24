package com.matheus.zeDeliveryDesafio.application.exception;

public class PointOutOfCoverageException extends RuntimeException{
    public PointOutOfCoverageException(double longitude, double latitude) {
        super(String.format("Point(%f,%f) is out of coverage",longitude,latitude));
    }
}
