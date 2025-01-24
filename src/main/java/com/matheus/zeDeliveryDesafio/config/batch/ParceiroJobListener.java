package com.matheus.zeDeliveryDesafio.config.batch;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

import java.util.concurrent.CompletableFuture;

public class ParceiroJobListener implements JobExecutionListener {
    private CompletableFuture<String> future;

    public ParceiroJobListener(CompletableFuture<String> future) {
        this.future = future;
    }

    @Override
    public void afterJob(JobExecution execution){
        if(BatchStatus.COMPLETED == execution.getStatus())
            future.complete("JOB COMPLETED");
        else{
            future.completeExceptionally(new RuntimeException("JOB FAILED: " + execution.getAllFailureExceptions().get(0).getCause().getMessage()));
        }
    }
}
