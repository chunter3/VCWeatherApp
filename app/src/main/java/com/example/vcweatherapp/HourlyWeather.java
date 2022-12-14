package com.example.vcweatherapp;

public class HourlyWeather {

    private final String day;
    private final String time;
    private final String temp;
    private final String condition;
    private final String hourlyIcon;

    // HourlyWeather obj holds hourly weather info that's seen on the 48-hour weather forecast on the home screen
    public HourlyWeather(String day, String time, String temp, String condition, String hourlyIcon) {
        this.day = day;
        this.time = time;
        this.temp = temp;
        this.condition = condition;
        this.hourlyIcon = hourlyIcon;
    }

    String getDay() {
        return day;
    }
    String getTime() {
        return time;
    }
    String getTemp(boolean fahrenheit) {
        if (fahrenheit) {
            return temp + "°F";
        }
        return temp + "°C";
    }
    String getDescription() {
        return condition;
    }
    String getHourlyIconID() {
        return hourlyIcon;
    }
}

