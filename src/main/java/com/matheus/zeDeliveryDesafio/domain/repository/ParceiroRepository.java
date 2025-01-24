package com.matheus.zeDeliveryDesafio.domain.repository;

import com.matheus.zeDeliveryDesafio.domain.entity.Parceiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ParceiroRepository extends JpaRepository<Parceiro,Long> {

    @Query(value = "SELECT * FROM parceiro \n" +
            "WHERE ST_Contains(parceiro.coverage_area,ST_SetSRID(ST_MakePoint(:longitude,\n" +
            "             :latitude),4326))\n" +
            "ORDER BY ST_Distance(parceiro.address,ST_SetSRID(ST_MakePoint(:longitude,\n" +
            "             :latitude),4326)) limit 1",
            nativeQuery = true)
    Optional<Parceiro> findNearest(@Param("longitude") double longitude, @Param("latitude") double latitude);
}
