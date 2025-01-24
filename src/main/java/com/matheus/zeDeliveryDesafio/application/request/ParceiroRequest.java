package com.matheus.zeDeliveryDesafio.application.request;


import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;

public record ParceiroRequest(
         Long id,
        String tradingName,
        String ownerName,
        String document,
        MultiPolygon coverageArea,
        Point address
) {
}
