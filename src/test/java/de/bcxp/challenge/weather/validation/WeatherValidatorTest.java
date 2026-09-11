package de.bcxp.challenge.weather.validation;

import de.bcxp.challenge.common.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WeatherValidatorTest {

    private final WeatherValidator validator = new WeatherValidator();

    @Test
    void acceptsValidWeatherData() {
        assertDoesNotThrow(() -> validator.validate(14, 61, 59));
    }

    @Test
    void rejectsNonPositiveDay() {
        assertThrows(ValidationException.class, () -> validator.validate(0, 61, 59));
    }

    @Test
    void rejectsNonFiniteTemperature() {
        assertThrows(ValidationException.class,
                () -> validator.validate(1, Double.NaN, 59));
    }

    @Test
    void rejectsMaximumBelowMinimum() {
        assertThrows(ValidationException.class, () -> validator.validate(1, 5, 10));
    }
}
