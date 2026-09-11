package de.bcxp.challenge.common.reader;

import de.bcxp.challenge.common.exception.DataImportException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class CSVFileReader implements DataFileReader<CSVRecord> {

    private final char delimiter;

    public CSVFileReader() {
        this(',');
    }

    public CSVFileReader(char delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    public List<CSVRecord> read(Reader reader) {
        try {
            CSVParser parser = CSVFormat.DEFAULT.builder()
                    .setDelimiter(delimiter)
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .get()
                    .parse(reader);
            return parser.getRecords();
        } catch (IOException e) {
            throw new DataImportException("Could not read CSV data", e);
        }
    }
}
