package de.bcxp.challenge.weather.domain;

public class WeatherRecord {

    private final int day;
    private final double maxTemperature;
    private final double minTemperature;

    public WeatherRecord(int day, double maxTemperature, double minTemperature) {
        this.day = day;
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
    }

    public double calculateTemperatureSpread() {
        return maxTemperature - minTemperature;
    }


    public int getDay() {
        return day;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

}
