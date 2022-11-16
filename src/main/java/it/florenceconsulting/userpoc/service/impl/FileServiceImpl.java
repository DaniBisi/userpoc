/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.florenceconsulting.userpoc.service.impl;

import it.florenceconsulting.userpoc.dto.UserDto;
import it.florenceconsulting.userpoc.service.FileService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public List<UserDto> loadFromCsv(MultipartFile file) throws UnsupportedEncodingException, IOException {
        InputStream inputStream = file.getInputStream();
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
        //
        List<CSVRecord> records = csvParser.getRecords();
        List<UserDto> collect = records.stream().map((csvRecord)
                -> UserDto.builder()
                        .id(Long.parseLong(csvRecord.get("Id")))
                        .username(csvRecord.get("Username"))
                        .email(csvRecord.get("Email"))
                        .firstname(csvRecord.get("Firstname"))
                        .lastname(csvRecord.get("Lastname"))
                        .cellphone(csvRecord.get("Cellphone"))
                        .build()
        ).collect(Collectors.toList());
        return collect;
    }

}
