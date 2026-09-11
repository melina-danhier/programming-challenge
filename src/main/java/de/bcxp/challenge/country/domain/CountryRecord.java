package de.bcxp.challenge.country.domain;

public class CountryRecord {

    private final String name;
    private final long population;
    private final int area;

    public CountryRecord(String name, long population, int area) {
        this.name = name;
        this.population = population;
        this.area = area;
    }

    public double calculatePopulationDensity() {
        return (double) population / area;
    }


    public String getName() {
        return name;
    }

    public long getPopulation() {
        return population;
    }

    public int getArea() {
        return area;
    }

}
