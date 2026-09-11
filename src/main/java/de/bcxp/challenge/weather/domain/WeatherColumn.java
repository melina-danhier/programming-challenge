package de.bcxp.challenge.weather.domain;

public enum WeatherColumn {

    DAY("Day"),
    MAX_TEMPERATURE("MxT"),
    MIN_TEMPERATURE("MnT");

    private final String header;

    WeatherColumn(String header) {
        this.header = header;
    }

    public String getHeader() {
        return header;
    }
}
