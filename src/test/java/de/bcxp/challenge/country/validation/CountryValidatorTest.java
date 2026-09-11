package de.bcxp.challenge.country.validation;

import de.bcxp.challenge.common.exception.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CountryValidatorTest {

    private final CountryValidator validator = new CountryValidator();

    @Test
    void acceptsValidCountryData() {
        assertDoesNotThrow(() -> validator.validate("Malta", 516_100, 316));
    }

    @Test
    void rejectsBlankName() {
        assertThrows(ValidationException.class, () -> validator.validate(" ", 100, 10));
    }

    @Test
    void rejectsNegativePopulation() {
        assertThrows(ValidationException.class, () -> validator.validate("Invalid", -1, 10));
    }

    @Test
    void rejectsNonPositiveArea() {
        assertThrows(ValidationException.class, () -> validator.validate("Invalid", 100, 0));
    }
}
