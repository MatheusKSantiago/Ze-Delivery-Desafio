package com.matheus.zeDeliveryDesafio.domain.entity;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.matheus.zeDeliveryDesafio.application.request.deserializer.MultiPolygonDeserializer;
import com.matheus.zeDeliveryDesafio.application.request.deserializer.PointDeserializer;
import jakarta.persistence.*;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;

@Entity
@Table(name="parceiro")
public class Parceiro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "trading_name", nullable = false,unique = true)
    private String tradingName;

    @Column(name = "owner_name", nullable = false)
    private String ownerName;

    @Column(name = "document", nullable = false,unique = true)
    private String document;

    @Column(name = "coverage_area", columnDefinition = "Geometry(MultiPolygon, 4326)")
    @JsonDeserialize(using = MultiPolygonDeserializer.class)
    private MultiPolygon coverageArea;

    @Column(name = "address", columnDefinition = "Geometry(Point, 4326)")
    @JsonDeserialize(using = PointDeserializer.class)
    private Point address;

    public void setId(Long id) {
        this.id = id;
    }

    public void setTradingName(String tradingName) {
        this.tradingName = tradingName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public void setCoverageArea(MultiPolygon coverageArea) {
        this.coverageArea = coverageArea;
    }

    public void setAddress(Point address) {
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public String getTradingName() {
        return tradingName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getDocument() {
        return document;
    }

    public MultiPolygon getCoverageArea() {
        return coverageArea;
    }

    public Point getAddress() {
        return address;
    }
}
