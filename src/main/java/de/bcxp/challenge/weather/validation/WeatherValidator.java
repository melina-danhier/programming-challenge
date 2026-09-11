package de.bcxp.challenge.weather.validation;

import de.bcxp.challenge.common.exception.ValidationException;

public class WeatherValidator {

    public void validate(int day, double maxTemperature, double minTemperature) {
        if (day <= 0) {
            throw new ValidationException("Day must be positive");
        }
        if (!Double.isFinite(maxTemperature) || !Double.isFinite(minTemperature)) {
            throw new ValidationException("Temperatures must be finite numbers");
        }
        if (maxTemperature < minTemperature) {
            throw new ValidationException("Maximum temperature cannot be lower than minimum temperature");
        }
    }
}
