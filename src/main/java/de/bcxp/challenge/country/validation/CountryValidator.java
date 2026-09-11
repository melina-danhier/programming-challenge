package de.bcxp.challenge.country.validation;

import de.bcxp.challenge.common.exception.ValidationException;

public class CountryValidator {

    public void validate(String name, long population, int area) {
        if (name == null || name.isBlank()) {
            throw new ValidationException("Country name cannot be blank");
        }
        if (population < 0) {
            throw new ValidationException("Population cannot be negative");
        }
        if (area <= 0) {
            throw new ValidationException("Area must be positive");
        }
    }
}
