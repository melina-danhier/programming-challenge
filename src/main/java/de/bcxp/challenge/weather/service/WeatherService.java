package de.bcxp.challenge.weather.service;

import de.bcxp.challenge.weather.domain.WeatherRecord;

import java.util.List;

public class WeatherService {

    public int getDayWithSmallestTempSpread(List<WeatherRecord> weatherData) {
        if (weatherData == null || weatherData.isEmpty()) {
            throw new IllegalArgumentException("Weather data cannot be null or empty");
        }

        WeatherRecord dayWithSmallestSpread = weatherData.get(0);
        for (WeatherRecord record : weatherData) {
            if (record.calculateTemperatureSpread() < dayWithSmallestSpread.calculateTemperatureSpread()) {
                dayWithSmallestSpread = record;
            }
        }
        return dayWithSmallestSpread.getDay();
    }
}
