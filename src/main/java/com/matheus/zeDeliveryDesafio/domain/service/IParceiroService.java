package com.matheus.zeDeliveryDesafio.domain.service;

import com.matheus.zeDeliveryDesafio.domain.entity.Parceiro;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

public interface IParceiroService {

    Parceiro findParceiroById(long id);
    Parceiro findNearestParceiro(double longitude, double latitude);
    CompletableFuture<String> saveParceiroInBatch(MultipartFile file) throws Exception;
}
