package de.bcxp.challenge.weather.mapper;

import de.bcxp.challenge.common.exception.DataImportException;
import de.bcxp.challenge.common.exception.ValidationException;
import de.bcxp.challenge.common.mapper.DataMapper;
import de.bcxp.challenge.weather.domain.WeatherColumn;
import de.bcxp.challenge.weather.domain.WeatherRecord;
import de.bcxp.challenge.weather.validation.WeatherValidator;
import org.apache.commons.csv.CSVRecord;

public class WeatherCSVMapper implements DataMapper<CSVRecord, WeatherRecord> {

    private final WeatherValidator validator;

    public WeatherCSVMapper() {
        this(new WeatherValidator());
    }

    public WeatherCSVMapper(WeatherValidator validator) {
        this.validator = validator;
    }

    @Override
    public WeatherRecord map(CSVRecord record) {
        try {
            int day = parseInt(record, WeatherColumn.DAY);
            double maxTemperature = parseDouble(record, WeatherColumn.MAX_TEMPERATURE);
            double minTemperature = parseDouble(record, WeatherColumn.MIN_TEMPERATURE);
            validator.validate(day, maxTemperature, minTemperature);
            return new WeatherRecord(day, maxTemperature, minTemperature);
        } catch (ValidationException e) {
            throw new DataImportException("Invalid weather data at CSV record " + record.getRecordNumber()
                    + ": " + e.getMessage(), e);
        }
    }

    private int parseInt(CSVRecord record, WeatherColumn column) {
        String value = value(record, column);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw invalidNumber(record, column, value, e);
        }
    }

    private double parseDouble(CSVRecord record, WeatherColumn column) {
        String value = value(record, column);
        try {
            double number = Double.parseDouble(value);
            if (!Double.isFinite(number)) {
                throw new NumberFormatException("not finite");
            }
            return number;
        } catch (NumberFormatException e) {
            throw invalidNumber(record, column, value, e);
        }
    }

    private String value(CSVRecord record, WeatherColumn column) {
        try {
            return record.get(column.getHeader()).trim();
        } catch (IllegalArgumentException e) {
            throw new DataImportException("Missing required CSV column '" + column.getHeader() + "'", e);
        }
    }

    private DataImportException invalidNumber(
            CSVRecord record, WeatherColumn column, String value, NumberFormatException cause
    ) {
        return new DataImportException("Invalid number '" + value + "' in column '" + column.getHeader()
                + "' at CSV record " + record.getRecordNumber(), cause);
    }
}
