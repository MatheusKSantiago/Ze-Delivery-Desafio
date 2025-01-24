package com.matheus.zeDeliveryDesafio.config.batch;

import com.matheus.zeDeliveryDesafio.domain.entity.Parceiro;
import com.matheus.zeDeliveryDesafio.domain.repository.ParceiroRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParceiroItemWriter implements ItemWriter<Parceiro> {
    private ParceiroRepository parceiroRepository;

    public ParceiroItemWriter(ParceiroRepository parceiroRepository) {
        this.parceiroRepository = parceiroRepository;
    }

    @Override
    public void write(Chunk<? extends Parceiro> chunk) throws Exception {
        chunk.getItems().stream().forEach(p -> p.setId(null));
        parceiroRepository.saveAll(chunk.getItems());
    }
}
