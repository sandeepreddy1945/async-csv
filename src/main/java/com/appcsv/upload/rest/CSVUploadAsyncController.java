package com.appcsv.upload.rest;

import com.appcsv.upload.components.UploadComponent;
import com.appcsv.upload.entity.User;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(path = "api")
public class CSVUploadAsyncController {

    private UploadComponent uploadComponent;

    @Autowired
    public CSVUploadAsyncController(UploadComponent uploadComponent) {
        this.uploadComponent = uploadComponent;
    }

    @PostMapping(path = "upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SimpleMessage> uploadCsvFile(@RequestPart("data") MultipartFile multipartFile) throws IOException {

        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = csvMapper.schemaFor(User.class).withHeader();
        byte[] inputStream = multipartFile.getBytes();
        MappingIterator<User> mappingIterator = csvMapper.readerFor(User.class).with(csvSchema).readValues(inputStream);
        uploadComponent.doSomeHeavyProcess(mappingIterator.readAll());
        return ResponseEntity.ok(new SimpleMessage("Request Received and Processing the file"));
    }
}
