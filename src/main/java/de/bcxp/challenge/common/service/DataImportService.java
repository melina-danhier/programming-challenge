package de.bcxp.challenge.common.service;

import de.bcxp.challenge.common.mapper.DataMapper;
import de.bcxp.challenge.common.exception.DataImportException;
import de.bcxp.challenge.common.exception.DataFileNotFoundException;
import de.bcxp.challenge.common.reader.DataFileReader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class DataImportService<F, T> {

    private final DataFileReader<F> dataFileReader;
    private final DataMapper<F, T> mapper;

    public DataImportService(DataFileReader<F> dataFileReader, DataMapper<F, T> mapper) {
        this.dataFileReader = dataFileReader;
        this.mapper = mapper;
    }

    public List<T> importDataFromResources(String filePath) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
        if (inputStream == null) {
            throw new DataFileNotFoundException("Data file not found: " + filePath);
        }
        try (Reader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return importDataFromReader(reader);
        } catch (IOException e) {
            throw new DataImportException("Could not close data resource: " + filePath, e);
        }
    }

    public List<T> importDataFromReader(Reader reader) {
        List<F> records = dataFileReader.read(reader);
        return records.stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }
}
