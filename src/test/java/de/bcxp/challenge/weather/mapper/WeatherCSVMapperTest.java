package de.bcxp.challenge.weather.mapper;

import de.bcxp.challenge.common.exception.DataImportException;
import de.bcxp.challenge.common.reader.CSVFileReader;
import de.bcxp.challenge.common.service.DataImportService;
import de.bcxp.challenge.weather.domain.WeatherRecord;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WeatherCSVMapperTest {

    private final DataImportService<CSVRecord, WeatherRecord> importer =
            new DataImportService<>(new CSVFileReader(), new WeatherCSVMapper());

    @Test
    void mapsRequiredColumnsByHeaderRegardlessOfTheirOrder() {
        WeatherRecord record = importSingle("MnT,unused,Day,MxT\n59,broken,14,61\n");

        assertEquals(14, record.getDay());
        assertEquals(2.0, record.calculateTemperatureSpread(), 0.0001);
    }

    @Test
    void rejectsMaximumBelowMinimumWithRecordContext() {
        DataImportException exception = assertThrows(DataImportException.class,
                () -> importSingle("Day,MxT,MnT\n1,5,10\n"));

        assertTrue(exception.getMessage().contains("record 1"));
        assertTrue(exception.getMessage().contains("Maximum temperature"));
    }

    @Test
    void rejectsInvalidNumberAndReportsColumnAndRecord() {
        DataImportException exception = assertThrows(DataImportException.class,
                () -> importSingle("Day,MxT,MnT\n1,not-a-number,10\n"));

        assertTrue(exception.getMessage().contains("MxT"));
        assertTrue(exception.getMessage().contains("record 1"));
    }

    @Test
    void rejectsMissingRequiredHeader() {
        DataImportException exception = assertThrows(DataImportException.class,
                () -> importSingle("Day,Maximum,MnT\n1,20,10\n"));

        assertTrue(exception.getMessage().contains("MxT"));
    }

    private WeatherRecord importSingle(String csv) {
        List<WeatherRecord> records = importer.importDataFromReader(new StringReader(csv));
        return records.get(0);
    }
}
