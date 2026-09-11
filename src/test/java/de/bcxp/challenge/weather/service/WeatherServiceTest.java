package de.bcxp.challenge.weather.service;

import de.bcxp.challenge.weather.domain.WeatherRecord;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WeatherServiceTest {

    private final WeatherService service = new WeatherService();

    @Test
    void returnsDayWithSmallestTemperatureSpread() {
        WeatherRecord wideSpread = weather(1, 30, 10);
        WeatherRecord smallestSpread = weather(2, 18, 15);
        WeatherRecord mediumSpread = weather(3, 24, 16);

        int result = service.getDayWithSmallestTempSpread(
                Arrays.asList(wideSpread, smallestSpread, mediumSpread)
        );

        assertEquals(2, result);
    }

    @Test
    void keepsFirstDayWhenSeveralDaysHaveSameSmallestSpread() {
        int result = service.getDayWithSmallestTempSpread(Arrays.asList(
                weather(7, 20, 15),
                weather(8, 12, 7)
        ));

        assertEquals(7, result);
    }

    @Test
    void rejectsMissingWeatherData() {
        assertThrows(IllegalArgumentException.class,
                () -> service.getDayWithSmallestTempSpread(null));
        assertThrows(IllegalArgumentException.class,
                () -> service.getDayWithSmallestTempSpread(Collections.emptyList()));
    }

    private WeatherRecord weather(int day, double maximum, double minimum) {
        return new WeatherRecord(day, maximum, minimum);
    }
}
