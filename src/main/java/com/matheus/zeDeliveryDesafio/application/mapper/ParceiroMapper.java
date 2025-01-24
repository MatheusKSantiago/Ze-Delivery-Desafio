package com.matheus.zeDeliveryDesafio.application.mapper;

import com.matheus.zeDeliveryDesafio.application.response.ParceiroResponse;
import com.matheus.zeDeliveryDesafio.domain.entity.Parceiro;

public class ParceiroMapper {
    public static ParceiroResponse fromEntity(Parceiro parceiro){
        return new ParceiroResponse(parceiro.getTradingName(),
                parceiro.getOwnerName(), parceiro.getDocument(), parceiro.getCoverageArea(),parceiro.getAddress());
    }
}
