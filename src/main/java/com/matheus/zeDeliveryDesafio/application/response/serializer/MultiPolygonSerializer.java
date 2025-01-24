package com.matheus.zeDeliveryDesafio.application.response.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Polygon;

import java.io.IOException;

public class MultiPolygonSerializer extends JsonSerializer<MultiPolygon> {

    @Override
    public void serialize(MultiPolygon multiPolygon, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("type","MultiPolygon");
        jsonGenerator.writeFieldName("coordinates");
        jsonGenerator.writeStartArray();
        jsonGenerator.writeStartArray();
        for(int i = 0;i < multiPolygon.getNumGeometries();i++){
            jsonGenerator.writeStartArray();
            Polygon p = (Polygon) multiPolygon.getGeometryN(i);
            Coordinate[] coordinates = p.getCoordinates();
            for(int k=0;k< coordinates.length;k++){
                jsonGenerator.writeStartArray();
                jsonGenerator.writeNumber(coordinates[k].x);
                jsonGenerator.writeNumber(coordinates[k].y);
                jsonGenerator.writeEndArray();
            }
            jsonGenerator.writeEndArray();
        }

        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
}
