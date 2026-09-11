package de.bcxp.challenge;

import de.bcxp.challenge.common.exception.DataFileNotFoundException;
import de.bcxp.challenge.common.reader.CSVFileReader;
import de.bcxp.challenge.common.service.DataImportService;
import de.bcxp.challenge.country.domain.CountryRecord;
import de.bcxp.challenge.country.mapper.CountryCSVMapper;
import de.bcxp.challenge.country.service.CountryService;
import de.bcxp.challenge.weather.domain.WeatherRecord;
import de.bcxp.challenge.weather.mapper.WeatherCSVMapper;
import de.bcxp.challenge.weather.service.WeatherService;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DataImportIntegrationTest {

    @Test
    void importsWeatherResourceAndSolvesChallenge() {
        DataImportService<CSVRecord, WeatherRecord> importer =
                new DataImportService<>(new CSVFileReader(), new WeatherCSVMapper());

        List<WeatherRecord> records = importer.importDataFromResources(
                "de/bcxp/challenge/weather.csv"
        );

        assertEquals(30, records.size());
        assertEquals(14, new WeatherService().getDayWithSmallestTempSpread(records));
    }

    @Test
    void importsCountryResourceAndSolvesChallenge() {
        DataImportService<CSVRecord, CountryRecord> importer =
                new DataImportService<>(new CSVFileReader(';'), new CountryCSVMapper());

        List<CountryRecord> records = importer.importDataFromResources(
                "de/bcxp/challenge/countries.csv"
        );

        assertEquals(27, records.size());
        assertEquals("Malta", new CountryService().getCountryWithHighestPopulationDensity(records));
    }

    @Test
    void reportsMissingClasspathResource() {
        DataImportService<CSVRecord, WeatherRecord> importer =
                new DataImportService<>(new CSVFileReader(), new WeatherCSVMapper());

        assertThrows(DataFileNotFoundException.class,
                () -> importer.importDataFromResources("does-not-exist.csv"));
    }
}
