package com.matheus.zeDeliveryDesafio.application.response.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;

import java.io.IOException;

public class PointSerializer extends JsonSerializer<Point> {
    @Override
    public void serialize(Point point, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("type","Point");
        jsonGenerator.writeFieldName("coordinates");
        jsonGenerator.writeStartArray();
        jsonGenerator.writeNumber(point.getCoordinates()[0].x);
        jsonGenerator.writeNumber(point.getCoordinates()[0].y);
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
}
