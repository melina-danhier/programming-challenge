package de.bcxp.challenge;

import de.bcxp.challenge.common.reader.CSVFileReader;
import de.bcxp.challenge.country.mapper.CountryCSVMapper;
import de.bcxp.challenge.country.domain.CountryRecord;
import de.bcxp.challenge.country.service.CountryService;
import de.bcxp.challenge.weather.mapper.WeatherCSVMapper;
import de.bcxp.challenge.common.service.DataImportService;
import de.bcxp.challenge.weather.service.WeatherService;
import de.bcxp.challenge.weather.domain.WeatherRecord;
import org.apache.commons.csv.CSVRecord;

import java.util.List;

/**
 * The entry class for your solution. This class is only aimed as starting point and not intended as baseline for your software
 * design. Read: create your own classes and packages as appropriate.
 */
public final class App {

    private static final String WEATHER_DATA_FILE_PATH = "de/bcxp/challenge/weather.csv";
    private static final String COUNTRY_DATA_FILE_PATH = "de/bcxp/challenge/countries.csv";

    /**
     * This is the main entry method of your program.
     * @param args The CLI arguments passed
     */
    public static void main(String... args) {
        weatherTask();
        countryTask();
    }

    private static void weatherTask() {
        DataImportService<CSVRecord, WeatherRecord> weatherImportService = new DataImportService<>(
                new CSVFileReader(),
                new WeatherCSVMapper()
        );
        List<WeatherRecord> weatherData = weatherImportService.importDataFromResources(WEATHER_DATA_FILE_PATH);
        WeatherService weatherService = new WeatherService();

        int dayWithSmallestTempSpread = weatherService.getDayWithSmallestTempSpread(weatherData);
        System.out.printf("Day with smallest temperature spread: %s%n", dayWithSmallestTempSpread);
    }

    private static void countryTask() {
        DataImportService<CSVRecord, CountryRecord> countryImportService = new DataImportService<>(
                new CSVFileReader(';'),
                new CountryCSVMapper()
        );
        List<CountryRecord> countryData = countryImportService.importDataFromResources(COUNTRY_DATA_FILE_PATH);
        CountryService countryService = new CountryService();

        String countryWithHighestPopulationDensity = countryService.getCountryWithHighestPopulationDensity(countryData);
        System.out.printf("Country with highest population density: %s%n", countryWithHighestPopulationDensity);
    }
}
