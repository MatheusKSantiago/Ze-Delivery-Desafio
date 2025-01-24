package com.matheus.zeDeliveryDesafio.config.batch;

import com.matheus.zeDeliveryDesafio.domain.entity.Parceiro;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.nio.file.Paths;

@Configuration
public class BatchConfig {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Bean
    public JsonItemReader<Parceiro> itemReader(){
        return new JsonItemReaderBuilder<Parceiro>()
                .jsonObjectReader(new JacksonJsonObjectReader<>(Parceiro.class))
                .resource(new FileSystemResource(Paths.get(uploadDir,"none.json")))
                .name("parceiroJsonItemReader")
                .build();
    }
    @Bean
    public Step step(JobRepository jobRepository,
                     ParceiroItemWriter parceiroItemWriter,
                     PlatformTransactionManager transactionManager) {
        return new StepBuilder("parceiroStep", jobRepository)
                .<Parceiro, Parceiro>chunk(10, transactionManager)
                .reader(itemReader())
                .writer(parceiroItemWriter)
                .build();
    }
}
