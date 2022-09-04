package com.appcsv.upload.faker;

import com.appcsv.upload.entity.User;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.github.javafaker.Faker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FakerDataCreator {
    public static void main(String[] args) throws IOException {
        Faker faker = new Faker();

        int count = 10000;

        List<User> users = new ArrayList<>();
        for(int i = 0; i < count; i++) {
            User user = new User();
            user.setName(faker.name().name());
            user.setAddress(faker.address().city());
            users.add(user);
        }

        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = csvMapper.schemaFor(User.class).withHeader();
        csvMapper.writer().with(csvSchema).writeValues(new File("user.csv")).writeAll(users).flush();
    }
}
