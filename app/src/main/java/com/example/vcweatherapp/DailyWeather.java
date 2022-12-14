package com.example.vcweatherapp;

public class DailyWeather {

    private final String dayDate;
    private final String highTemp;
    private final String lowTemp;
    private final String description;
    private final String precipProb;
    private final String uvIndex;
    private final String morningTemp;
    private final String afternoonTemp;
    private final String eveningTemp;
    private final String nightTemp;
    private final String dailyIcon;

    // DailyWeather obj holds daily weather info that's seen on the seperate 15-day weather forecast activity
    public DailyWeather(String dayDate, String highTemp, String lowTemp, String description, String precipProb, String uvIndex, String morningTemp, String afternoonTemp, String eveningTemp, String nightTemp, String dailyIcon) {
        this.dayDate = dayDate;
        this.highTemp = highTemp;
        this.lowTemp = lowTemp;
        this.description = description;
        this.precipProb = precipProb;
        this.uvIndex = uvIndex;
        this.morningTemp = morningTemp;
        this.afternoonTemp = afternoonTemp;
        this.eveningTemp = eveningTemp;
        this.nightTemp = nightTemp;
        this.dailyIcon = dailyIcon;
    }

    String getDayDate() {
        return dayDate;
    }
    String getHighTemp(boolean fahrenheit) {
        if (fahrenheit) {
            return highTemp + "°F";
        }
        return highTemp + "°C";
    }
    String getLowTemp(boolean fahrenheit) {
        if (fahrenheit) {
            return lowTemp + "°F";
        }
        return lowTemp + "°C";
    }
    String getDescription() {
        return description;
    }
    String getPrecipProb() {
        return "(" + precipProb + "% precip.)";
    }
    String getUVIndex() {
        return "UV Index: " + uvIndex;
    }
    String getMorningTemp(boolean fahrenheit) {
        if (fahrenheit) {
            return morningTemp + "°F";
        }
        return morningTemp + "°C";
    }
    String getAfternoonTemp(boolean fahrenheit) {
        if (fahrenheit) {
            return afternoonTemp + "°F";
        }
        return afternoonTemp + "°C";
    }
    String getEveningTemp(boolean fahrenheit) {
        if (fahrenheit) {
            return eveningTemp + "°F";
        }
        return eveningTemp + "°C";
    }
    String getNightTemp(boolean fahrenheit) {
        if (fahrenheit) {
            return nightTemp + "°F";
        }
        return nightTemp + "°C";
    }
    String getDailyIcon() {
        return dailyIcon;
    }
}

