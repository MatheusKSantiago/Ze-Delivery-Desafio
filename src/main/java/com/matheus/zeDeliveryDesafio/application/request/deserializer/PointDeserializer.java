package com.matheus.zeDeliveryDesafio.application.request.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.matheus.zeDeliveryDesafio.application.exception.AddressWrongTypeException;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import java.io.IOException;

public class PointDeserializer extends JsonDeserializer<Point> {
    @Override
    public Point deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String type = node.get("type").toString();
        if(!type.replace("\"","").equals("Point"))
            throw new AddressWrongTypeException(type);
        String value = node.get("coordinates").toString();
        value = value.substring(1,value.length()-1);
        String[] values = value.split(",");
        var fac = new GeometryFactory();

        return fac.createPoint(new Coordinate(Double.parseDouble(values[0]), Double.parseDouble(values[1])));

    }
}
