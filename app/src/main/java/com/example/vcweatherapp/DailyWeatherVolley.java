package com.example.vcweatherapp;

import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DailyWeatherVolley {

    private static final String TAG = "DailyWeatherVolley";
    private static RequestQueue queue;
    private static ArrayList<DailyWeather> dailyWeatherList = new ArrayList<>();
    private static final String timelineWeatherURL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline";
    private static final String myAPIKey = "RL8XLPB7ZSDW5ZLUKS7ZLLW74"; // API key from free Visual Crossing account


    public static void downloadWeather(DailyForecastActivity dailyForecastActivityIn, String location, boolean fahrenheit) {

        queue = Volley.newRequestQueue(dailyForecastActivityIn);

        Uri.Builder buildURL = Uri.parse(timelineWeatherURL).buildUpon();
        buildURL.appendPath(location);
        buildURL.appendQueryParameter("unitGroup", (fahrenheit ? "us" : "metric")); // unitGroup determines the unit of measurement ("us" = Fahrenheit; "metric" = Celsius)
        buildURL.appendQueryParameter("lang", "en");
        buildURL.appendQueryParameter("key", myAPIKey);
        String urlToUse = buildURL.build().toString();

        Response.Listener<JSONObject> listener = response -> handleResult(dailyForecastActivityIn, response.toString());

        Response.ErrorListener error = error1 -> handleResult(dailyForecastActivityIn, null);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlToUse, null, listener, error);
        queue.add(jsonObjectRequest);
    }

    private static void handleResult(DailyForecastActivity dailyForecastActivityIn, String string) {
        if (string == null) {
            dailyForecastActivityIn.downloadFailed();
            return;
        }
        final ArrayList<DailyWeather> dailyForecastScreen = parseJSON(string);
        dailyForecastActivityIn.updateForecast(dailyForecastScreen);
    }

    private static ArrayList<DailyWeather> parseJSON(String string) {
        try {
            JSONObject jObjMain = new JSONObject(string);
            JSONArray dailyWeather = jObjMain.getJSONArray("days");

            for (int i = 0; i < dailyWeather.length(); i++) {  // Capturing all daily weather info
                JSONObject jDailyWeather = (JSONObject) dailyWeather.get(i);
                JSONArray dailyWeatherHours = jDailyWeather.getJSONArray("hours");
                String dayDate = convertEpoch(jDailyWeather.getString("datetimeEpoch"), false, true, false);
                String highTemp = jDailyWeather.getString("tempmax");
                String lowTemp = jDailyWeather.getString("tempmin");
                String description = jDailyWeather.getString("description");
                String precipProb = jDailyWeather.getString("precipprob");
                String uvIndex = jDailyWeather.getString("uvindex");
                String morningTemp = getHourlyTemp(dailyWeatherHours, 8);
                String afternoonTemp = getHourlyTemp(dailyWeatherHours, 13);
                String eveningTemp = getHourlyTemp(dailyWeatherHours, 17);
                String nightTemp = getHourlyTemp(dailyWeatherHours, 23);
                String dailyIcon = jDailyWeather.getString("icon");

                dailyWeatherList.add(new DailyWeather(dayDate, highTemp, lowTemp, description, precipProb, uvIndex, morningTemp, afternoonTemp, eveningTemp, nightTemp, dailyIcon));
            }
        } catch (Exception e) {
            Log.d(TAG, "parseJSON: " + e.getMessage());
            e.printStackTrace();
        }
        return dailyWeatherList;
    }

    private static String getHourlyTemp(JSONArray dailyWeatherHours, int timeOfday) throws JSONException {
        JSONObject jDailyWeatherHours = (JSONObject) dailyWeatherHours.get(timeOfday);
        return jDailyWeatherHours.getString("temp");
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
