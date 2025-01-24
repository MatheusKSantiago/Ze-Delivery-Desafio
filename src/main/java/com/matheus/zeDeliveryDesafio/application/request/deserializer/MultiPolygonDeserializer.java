package com.matheus.zeDeliveryDesafio.application.request.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.matheus.zeDeliveryDesafio.application.exception.CoverageAreaWrongTypeException;
import com.matheus.zeDeliveryDesafio.application.exception.NotGeoJsonCoordinatesException;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Polygon;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MultiPolygonDeserializer extends JsonDeserializer<MultiPolygon> {

    @Override
    public MultiPolygon deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node =  p.getCodec().readTree(p);
        String  type = node.get("type").toString();

        if(!type.replace("\"","").equals("MultiPolygon"))
            throw new CoverageAreaWrongTypeException(type);

        String coo = node.get("coordinates").toString();
        coo = coo.substring(2,coo.length()-2);

        StringBuilder x = new StringBuilder();
        StringBuilder y = new StringBuilder();
        int openedPolygon = 0;
        boolean isX = true;

        List<Polygon> polygons = new ArrayList<>();
        List<Coordinate> coordinates = new ArrayList<>();
        var fac = new GeometryFactory();
        try {
            for (char c : coo.toCharArray()) {
                if (c == '[') {
                    openedPolygon++;
                } else if (c == ']') {
                    openedPolygon--;
                    if (openedPolygon == 1) {
                        coordinates.add(new Coordinate(
                                Double.parseDouble(x.toString()),
                                Double.parseDouble(y.toString())
                        ));
                        x.setLength(0);
                        y.setLength(0);
                        isX = true;
                    } else if (openedPolygon == 0) {
                        polygons.add(fac.createPolygon(coordinates.toArray(new Coordinate[0])));
                        coordinates.clear();
                    }
                } else if (c == ',' && openedPolygon == 2) {
                    isX = false;
                } else if (c != ',') {
                    if (isX) {
                        x.append(c);
                    } else {
                        y.append(c);
                    }
                }
            }

            return fac.createMultiPolygon(polygons.toArray(new Polygon[0]));
        }catch(RuntimeException e){
            throw new NotGeoJsonCoordinatesException();
        }
    }
}
