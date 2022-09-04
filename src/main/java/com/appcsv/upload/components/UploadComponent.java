package com.appcsv.upload.components;

import com.appcsv.upload.entity.User;
import com.appcsv.upload.service.HeavyLiftService;
import com.appcsv.upload.service.ValidationService;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

@Component
@Slf4j
public class UploadComponent {

    private ValidationService validationService;
    private HeavyLiftService heavyLiftService;
    private Executor executor;

    @Autowired
    public UploadComponent(ValidationService validationService, HeavyLiftService heavyLiftService,
                           @Qualifier("csvUploadThreadPoolTaskExecutor") Executor executor) {
        this.validationService = validationService;
        this.heavyLiftService = heavyLiftService;
        this.executor = executor;
    }

    @Async("csvUploadThreadPoolTaskExecutor")
    public void doSomeHeavyProcess(List<User> userList) throws IOException {


        log.info("File Parsing Completed ... Processing Records ...");
        for (User user : userList) {
            validationService.validateEntry(user)
                    .thenComposeAsync(returnParam -> heavyLiftService.performOperation(returnParam), executor)
                    .whenCompleteAsync((User returnParam, Throwable exception) -> {
                        if (Objects.isNull(exception)) {
                            log.info("Entry Process completed for user: {}", returnParam);
                        } else {
                            log.error("There was an error for the user process : {}", user, exception);
                        }
                    }, executor);

        }
    }
}
