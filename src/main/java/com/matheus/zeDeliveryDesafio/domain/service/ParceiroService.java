package com.matheus.zeDeliveryDesafio.domain.service;

import com.matheus.zeDeliveryDesafio.application.exception.JsonFileRequiredException;
import com.matheus.zeDeliveryDesafio.application.exception.ParceiroNotFoundException;
import com.matheus.zeDeliveryDesafio.application.exception.PointOutOfCoverageException;
import com.matheus.zeDeliveryDesafio.config.batch.ParceiroJobListener;
import com.matheus.zeDeliveryDesafio.domain.entity.Parceiro;
import com.matheus.zeDeliveryDesafio.domain.repository.ParceiroRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.batch.core.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class ParceiroService implements IParceiroService{
    @Value("${file.upload-dir}")
    private String uploadDir;
    private ParceiroRepository parceiroRepository;
    private JobRepository jobRepository;
    private JobLauncher launcher;
    private Step step;
    private JsonItemReader<Parceiro> itemReader;

    public ParceiroService(ParceiroRepository parceiroRepository,
                           JobRepository jobRepository,
                           JobLauncher launcher,
                           Step step, JsonItemReader<Parceiro> itemReader) {
        this.parceiroRepository = parceiroRepository;
        this.jobRepository = jobRepository;
        this.launcher = launcher;
        this.step = step;
        this.itemReader = itemReader;
    }

    @PostConstruct
    public void initDir() throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if(Files.notExists(uploadPath)){
            Files.createDirectory(uploadPath);
        }
    }

    @Override
    public Parceiro findParceiroById(long id) {
        return parceiroRepository.findById(id).orElseThrow(()->new ParceiroNotFoundException(id));
    }

    @Override
    public Parceiro findNearestParceiro(double longitude, double latitude) {
        return parceiroRepository.findNearest(longitude,latitude).orElseThrow(()->new PointOutOfCoverageException(longitude, latitude));
    }

    @Override
    public CompletableFuture<String> saveParceiroInBatch(MultipartFile file) throws Exception{
        var future = new CompletableFuture<String>();

        if(file.isEmpty() || !file.getContentType().equals(MediaType.APPLICATION_JSON.toString())){
            throw new JsonFileRequiredException();
        }

        Path destPath = Paths.get(uploadDir,UUID.randomUUID().toString()+".json");
        file.transferTo(destPath);
        itemReader.setResource(new FileSystemResource(destPath));

        var parceiroJob = new JobBuilder("parceiro-job-"+UUID.randomUUID(),jobRepository)
                .listener(new ParceiroJobListener(future))
                .start(step).build();
        launcher.run(parceiroJob,new JobParameters());
        return future;

    }
}
