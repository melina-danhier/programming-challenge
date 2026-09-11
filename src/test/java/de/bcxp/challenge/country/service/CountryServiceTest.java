package de.bcxp.challenge.country.service;

import de.bcxp.challenge.country.domain.CountryRecord;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CountryServiceTest {

    private final CountryService service = new CountryService();

    @Test
    void returnsCountryWithHighestPopulationDensity() {
        CountryRecord largeButSparse = country("Large", 1_000, 100);
        CountryRecord smallAndDense = country("Dense", 600, 20);
        CountryRecord medium = country("Medium", 800, 50);

        String result = service.getCountryWithHighestPopulationDensity(
                Arrays.asList(largeButSparse, smallAndDense, medium)
        );

        assertEquals("Dense", result);
    }

    @Test
    void densityCalculationDoesNotUseIntegerDivision() {
        assertEquals(2.5, country("Example", 5, 2).calculatePopulationDensity(), 0.0001);
    }

    @Test
    void rejectsMissingCountryData() {
        assertThrows(IllegalArgumentException.class,
                () -> service.getCountryWithHighestPopulationDensity(null));
        assertThrows(IllegalArgumentException.class,
                () -> service.getCountryWithHighestPopulationDensity(Collections.emptyList()));
    }

    private CountryRecord country(String name, long population, int area) {
        return new CountryRecord(name, population, area);
    }
}
