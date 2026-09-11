package de.bcxp.challenge.country.service;

import de.bcxp.challenge.country.domain.CountryRecord;

import java.util.List;

public class CountryService {

    public String getCountryWithHighestPopulationDensity(List<CountryRecord> countryData) {
        if (countryData == null || countryData.isEmpty()) {
            throw new IllegalArgumentException("Country data cannot be null or empty");
        }

        CountryRecord highestDensityCountry = countryData.get(0);
        for (CountryRecord country : countryData) {
            if (country.calculatePopulationDensity() > highestDensityCountry.calculatePopulationDensity()) {
                highestDensityCountry = country;
            }
        }

        return highestDensityCountry.getName();
    }
}
