package com.appcsv.upload.service;

import com.appcsv.upload.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class HeavyLiftService {

    @Async("csvUploadThreadPoolTaskExecutor")
    @Retryable(value = Exception.class, backoff = @Backoff(delay = 1000))
    public CompletableFuture<User> performOperation(User user) {

        // do some process here....
        log.info("Performing heavy work on record: {}", user);
        return CompletableFuture.completedFuture(user);
    }
}
