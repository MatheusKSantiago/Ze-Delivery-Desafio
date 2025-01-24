package com.matheus.zeDeliveryDesafio.application.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.matheus.zeDeliveryDesafio.application.response.serializer.MultiPolygonSerializer;
import com.matheus.zeDeliveryDesafio.application.response.serializer.PointSerializer;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;


public record ParceiroResponse(
        String tradingName,
        String ownerName,
        String document,
        @JsonSerialize(using = MultiPolygonSerializer.class)
        MultiPolygon coverageArea,
        @JsonSerialize(using = PointSerializer.class)
        Point address
) {
}
