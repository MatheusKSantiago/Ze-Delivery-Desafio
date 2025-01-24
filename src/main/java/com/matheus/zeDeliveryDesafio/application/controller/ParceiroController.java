package com.matheus.zeDeliveryDesafio.application.controller;

import com.matheus.zeDeliveryDesafio.application.mapper.ParceiroMapper;
import com.matheus.zeDeliveryDesafio.application.response.ParceiroResponse;
import com.matheus.zeDeliveryDesafio.domain.service.IParceiroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/parceiro")
public class ParceiroController {

    public IParceiroService parceiroService;

    public ParceiroController(IParceiroService parceiroService) {
        this.parceiroService = parceiroService;
    }

    @PostMapping("/batch")
    public CompletableFuture<ResponseEntity<String>> saveInBatch(@RequestParam("file") MultipartFile file) throws Exception {
        CompletableFuture<String> future = parceiroService.saveParceiroInBatch(file);
        return future.thenApply(ResponseEntity::ok)
                .exceptionally(ex -> ResponseEntity.internalServerError().body(ex.getMessage()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParceiroResponse> findById(@PathVariable long id){
        return ResponseEntity.ok(ParceiroMapper.fromEntity(parceiroService.findParceiroById(id)));
    }
    @GetMapping("/{long}/{lat}")
    public ResponseEntity<ParceiroResponse> findNearest(@PathVariable("long") double longitude,
                                                        @PathVariable("lat") double latitude){
        return ResponseEntity.ok(ParceiroMapper.fromEntity(parceiroService.findNearestParceiro(longitude, latitude)));
    }
}
