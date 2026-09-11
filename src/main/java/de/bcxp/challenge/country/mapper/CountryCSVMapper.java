package de.bcxp.challenge.country.mapper;

import de.bcxp.challenge.common.exception.DataImportException;
import de.bcxp.challenge.common.exception.ValidationException;
import de.bcxp.challenge.common.mapper.DataMapper;
import de.bcxp.challenge.country.domain.CountryColumn;
import de.bcxp.challenge.country.domain.CountryRecord;
import de.bcxp.challenge.country.validation.CountryValidator;
import org.apache.commons.csv.CSVRecord;

public class CountryCSVMapper implements DataMapper<CSVRecord, CountryRecord> {

    private final CountryValidator validator;

    public CountryCSVMapper() {
        this(new CountryValidator());
    }

    public CountryCSVMapper(CountryValidator validator) {
        this.validator = validator;
    }

    @Override
    public CountryRecord map(CSVRecord record) {
        try {
            String name = value(record, CountryColumn.NAME);
            long population = parseLong(record, CountryColumn.POPULATION);
            int area = parseInt(record, CountryColumn.AREA);

            validator.validate(name, population, area);

            return new CountryRecord(name, population, area);
        } catch (ValidationException e) {
            throw new DataImportException(
                    "Invalid country data at CSV record "
                            + record.getRecordNumber() + ": " + e.getMessage(),
                    e
            );
        }
    }

    private long parseLong(CSVRecord record, CountryColumn column) {
        String value = value(record, column);

        try {
            return Long.parseLong(normalizeNumber(value));
        } catch (NumberFormatException e) {
            throw invalidNumber(record, column, value, e);
        }
    }

    private int parseInt(CSVRecord record, CountryColumn column) {
        String value = value(record, column);

        try {
            return Integer.parseInt(normalizeNumber(value));
        } catch (NumberFormatException e) {
            throw invalidNumber(record, column, value, e);
        }
    }

    private String normalizeNumber(String value) {
        if (value.matches("[+-]?\\d+")) {
            return value;
        }

        if (value.matches("[+-]?\\d{1,3}(\\.\\d{3})+(,0+)?")) {
            return value
                    .replace(".", "")
                    .replaceAll(",0+$", "");
        }

        throw new NumberFormatException("Unsupported number format");
    }

    private String value(CSVRecord record, CountryColumn column) {
        try {
            return record.get(column.getHeader()).trim();
        } catch (IllegalArgumentException e) {
            throw new DataImportException(
                    "Missing required CSV column '" + column.getHeader() + "'",
                    e
            );
        }
    }

    private DataImportException invalidNumber(
            CSVRecord record,
            CountryColumn column,
            String value,
            RuntimeException cause
    ) {
        return new DataImportException(
                "Invalid number '" + value
                        + "' in column '" + column.getHeader()
                        + "' at CSV record " + record.getRecordNumber(),
                cause
        );
    }
}