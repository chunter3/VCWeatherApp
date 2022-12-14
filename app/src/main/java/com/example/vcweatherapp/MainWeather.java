package com.example.vcweatherapp;

public class MainWeather {
    private final String dateTime;
    private final String temp;
    private final String feelsLike;
    private final String humidity;
    private final String uvIndex;
    private final String morningTemp;
    private final String afternoonTemp;
    private final String eveningTemp;
    private final String nightTemp;
    private final String condition;
    private final String cloudCover;
    private final String windDir;
    private final String windSpd;
    private final String windGust;
    private final String visibility;
    private final String sunrise;
    private final String sunset;
    private final String weatherIcon;

    // MainWeather obj holds current weather info that's seen on the home screen
    public MainWeather(String dateTime, String temp, String feelsLike, String humidity, String uvIndex, String morningTemp, String afternoonTemp, String eveningTemp, String nightTemp, String condition, String cloudCover, String windDir, String windSpd, String windGust, String visibility, String sunrise, String sunset, String weatherIcon) {
        this.dateTime = dateTime;
        this.temp = temp;
        this.feelsLike = feelsLike;
        this.humidity = humidity;
        this.uvIndex = uvIndex;
        this.morningTemp = morningTemp;
        this.afternoonTemp = afternoonTemp;
        this.eveningTemp = eveningTemp;
        this.nightTemp = nightTemp;
        this.condition = condition;
        this.cloudCover = cloudCover;
        this.windDir = windDir;
        this.windSpd = windSpd;
        this.windGust = windGust;
        this.visibility = visibility;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.weatherIcon = weatherIcon;
    }

    String getDateTime() {
        return dateTime;
    }
    String getTemp(boolean fahrenheit) {
        if (fahrenheit) {
            return temp + "°F";
        }
        return temp + "°C";
    }
    String getFeelsLike(boolean fahrenheit) {
        if (fahrenheit) {
            return "Feels Like " + feelsLike + "°F";
        }
        return "Feels Like " + feelsLike + "°C";
    }
    String getHumidity() {
        return "Humidity: " + humidity + "%";
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
    String getWeatherDescription() {
        return condition + " (" + cloudCover + "% clouds)";
    }
    String getWindsDescription(boolean fahrenheit) {
        if (fahrenheit) {
            return "Winds: " + getDirection(Double.parseDouble(windDir)) + " at " + windSpd + " mph gusting to " + windGust + " mph";
        }
            return "Winds: " + getDirection(Double.parseDouble(windDir)) + " at " + windSpd + " kph gusting to " + windGust + " kph";
    }
    String getVisibility(boolean fahrenheit) {
        if (fahrenheit) {
            return "Visibility: " + visibility + " mi";
        }
        return "Visibility: " + visibility + " km";
    }
    String getSunrise() {
        return "Sunrise: " + sunrise;
    }
    String getSunset() {
        return "Sunset: " + sunset;
    }
    String getWeatherIconID() {
        return weatherIcon;
    }

    private String getDirection(double degrees) {  // converts numerical wind direction into a conventional string
        if (degrees >= 337.5 || degrees < 22.5) {
            return "N";
        }
        if (degrees >= 22.5 && degrees < 67.5) {
            return "NE";
        }
        if (degrees >= 67.5 && degrees < 112.5) {
            return "E";
        }
        if (degrees >= 112.5 && degrees < 157.5) {
            return "SE";
        }
        if (degrees >= 157.5 && degrees < 202.5) {
            return "S";
        }
        if (degrees >= 202.5 && degrees < 247.5) {
            return "SW";
        }
        if (degrees >= 247.5 && degrees < 292.5) {
            return "W";
        }
        if (degrees >= 292.5 && degrees < 337.5) {
            return "NW";
        }
        return "X"; // we'll use 'X' as the default if we get a bad value
    }

}
