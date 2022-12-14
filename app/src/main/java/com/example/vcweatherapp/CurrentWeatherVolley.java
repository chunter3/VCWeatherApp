package com.example.vcweatherapp;

import android.net.Uri;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CurrentWeatherVolley {

    private static RequestQueue queue;
    private static MainWeather mainWeather;
    private static ArrayList<HourlyWeather> hourlyWeatherList = new ArrayList<>();
    private static final String timelineWeatherURL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline";
    private static final String myAPIKey = "RL8XLPB7ZSDW5ZLUKS7ZLLW74"; // API key from free Visual Crossing account
    private static final String TAG = "CurrentWeatherVolley";


    public static void downloadWeather(MainActivity mainActivityIn, String location, boolean fahrenheit) {

        queue = Volley.newRequestQueue(mainActivityIn);

        Uri.Builder buildURL = Uri.parse(timelineWeatherURL).buildUpon();
        buildURL.appendPath(location);
        buildURL.appendQueryParameter("unitGroup", (fahrenheit ? "us" : "metric")); // unitGroup determines the unit of measurement ("us" = Fahrenheit; "metric" = Celsius)
        buildURL.appendQueryParameter("lang", "en");
        buildURL.appendQueryParameter("key", myAPIKey);
        String urlToUse = buildURL.build().toString();

        Response.Listener<JSONObject> listener = response -> handleResult(mainActivityIn, response.toString());

        Response.ErrorListener error = error1 -> handleResult(mainActivityIn, null);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlToUse, null, listener, error);
        queue.add(jsonObjectRequest);
    }

    private static void handleResult(MainActivity mainActivityIn, String string) {
        if (string == null) {
            mainActivityIn.downloadFailed();
            return;
        }
        final MainWeather homeScreenWeather = parseJSON(string);
        mainActivityIn.updateHourlyWeather(hourlyWeatherList);
        mainActivityIn.updateHomeScreen(homeScreenWeather);
    }

    private static MainWeather parseJSON(String string) {
        try {
            JSONObject jObjMain = new JSONObject(string);

            JSONObject currWeather = jObjMain.getJSONObject("currentConditions"); // Capturing current weather info

            String currDayDate = convertEpoch(currWeather.getString("datetimeEpoch"), true, false, false);
            String currTemp = currWeather.getString("temp");
            String feelsLike = currWeather.getString("feelslike");
            String humidity = currWeather.getString("humidity");
            String currUVIndex = currWeather.getString("uvindex");
            String currCondition = currWeather.getString("conditions");
            String cloudCover = currWeather.getString("cloudcover");
            String windDir = currWeather.getString("winddir");
            String windSpd = currWeather.getString("windspeed");
            String windGust = currWeather.getString("windgust");
            String visibility = currWeather.getString("visibility");
            String sunrise = convertEpoch(currWeather.getString("sunriseEpoch"), false, false, false);
            String sunset = convertEpoch(currWeather.getString("sunsetEpoch"), false, false, false);
            String mainWeatherIcon = currWeather.getString("icon");

            String morningTemp = "";
            String afternoonTemp = "";
            String eveningTemp = "";
            String nightTemp = "";

            JSONArray days = jObjMain.getJSONArray("days");
            for (int i = 0; i < 4; i++) { // Capturing all hourly weather info
                JSONObject dayObj = (JSONObject) days.get(i);
                JSONArray hours = dayObj.getJSONArray("hours");
                for (int j = 0; j < hours.length(); j++) {
                    JSONObject hourObj = (JSONObject) hours.get(j);
                    String day;
                    if (i == 0) {
                        day = "Today";
                        if (j == 8) {
                            morningTemp = hourObj.getString("temp");
                        } else if (j == 13) {
                            afternoonTemp = hourObj.getString("temp");
                        } else if (j == 17) {
                            eveningTemp = hourObj.getString("temp");
                        } else if (j == 23) {
                            nightTemp = hourObj.getString("temp");
                        }
                    } else {
                        day = convertEpoch(hourObj.getString("datetimeEpoch"), false, false, true);
                    }
                    String time = convertEpoch(hourObj.getString("datetimeEpoch"), false, false, false);
                    String temp = hourObj.getString("temp");
                    String condition = hourObj.getString("conditions");
                    String hourlyIcon = hourObj.getString("icon");

                    hourlyWeatherList.add(new HourlyWeather(day, time, temp, condition, hourlyIcon));
                }
            }
            mainWeather = new MainWeather(currDayDate, currTemp, feelsLike, humidity, currUVIndex, morningTemp, afternoonTemp, eveningTemp, nightTemp, currCondition, cloudCover, windDir, windSpd, windGust, visibility, sunrise, sunset, mainWeatherIcon);
        } catch (Exception e) {
            Log.d(TAG, "parseJSON: " + e.getMessage());
            e.printStackTrace();
        }
        return mainWeather;
    }

    public static String convertEpoch(String dateTimeEpoch, boolean getFullDate, boolean getDayDate, boolean getDay) { // converts epochs into various formats as needed

        long dateTimeEpochLong = Long.parseLong(dateTimeEpoch);
        Date dateTime = new Date(dateTimeEpochLong * 1000);

        if (getFullDate) {
            SimpleDateFormat fullDate = new SimpleDateFormat("EEEE MMM dd h:mm a, yyyy", Locale.getDefault());
            return fullDate.format(dateTime);
        } else if (getDayDate) {
            SimpleDateFormat dayDate = new SimpleDateFormat("EEEE MM/dd", Locale.getDefault());
            return dayDate.format(dateTime);
        } else if (getDay) {
            SimpleDateFormat day = new SimpleDateFormat("EEEE", Locale.getDefault());
            return day.format(dateTime);
        } else {
            SimpleDateFormat time = new SimpleDateFormat("h:mm a", Locale.getDefault());
            return time.format(dateTime);
        }
    }
}




