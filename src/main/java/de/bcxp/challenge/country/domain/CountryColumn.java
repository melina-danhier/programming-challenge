package de.bcxp.challenge.country.domain;

public enum CountryColumn {

    NAME("Name"),
    POPULATION("Population"),
    AREA("Area (km²)");

    private final String header;

    CountryColumn(String header) {
        this.header = header;
    }

    public String getHeader() {
        return header;
    }
}
