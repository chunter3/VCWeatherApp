package com.example.vcweatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // INTERNET permission in AndroidManifest.xml in manifests folder
    // ACCESS_NETWORK_STATE permission in AndroidManifest.xml in manifests folder

    // Limitations/caveats: inputting a faulty location (e.g., "fhjbfdsbfs" or something random
    // like that) results in that input replacing the app title upon refreshing. The app cannot
    // properly handle bad location inputs; proper location inputs (e.g., "Kyoto, Japan") do work

    private RecyclerView hourlyRecyclerView;
    private HourlyWeatherAdapter hourlyWeatherAdapter;
    private LinearLayoutManager linearLayoutManager;
    private String initialLocation = "Chicago, IL"; // dynamic string value that determines the data the API endpoint returns (Chicago, IL is the default)
    private String newLocation = initialLocation;  // dynamic string that holds the new location inputted by user
    private boolean fahrenheit = true;  // dynamic boolean value that determines what metric the return data of the API endpoint is in
    private SwipeRefreshLayout swipeRefresher;
    private final List<HourlyWeather> hourlyWeatherList = new ArrayList<>();
    private final String TAG = getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefresher = findViewById(R.id.homeScreenSwipeRefresh);
        swipeRefresher.setOnRefreshListener(() -> {
            if (hasNetworkConnection()) {
                Toast.makeText(MainActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
            }
            else {
                doDownload(initialLocation, fahrenheit);
            }
            swipeRefresher.setRefreshing(false);
        });

        if (hasNetworkConnection()) {  // verifying is there's an internet connection
            this.setTitle("Weather App");
            TextView dateTime = findViewById(R.id.currDateTime);
            dateTime.setText(R.string.no_net);
            return;
        }
        hourlyRecyclerView = findViewById(R.id.hourlyForecastRecyclerView);
        hourlyWeatherAdapter = new HourlyWeatherAdapter(hourlyWeatherList, this, true);
        hourlyRecyclerView.setAdapter(hourlyWeatherAdapter);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        hourlyRecyclerView.setLayoutManager(linearLayoutManager);

        doDownload(initialLocation, fahrenheit);
        this.setTitle(initialLocation);
    }

    private boolean hasNetworkConnection() {
        ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo == null || !networkInfo.isConnectedOrConnecting());
    }

    private void doDownload(String location, boolean fahrenheit) {
        CurrentWeatherVolley.downloadWeather(this, location, fahrenheit);
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.toggleUnitsDailyForecast) {
            if (fahrenheit) {
                fahrenheit = false;
                hourlyRecyclerView = findViewById(R.id.hourlyForecastRecyclerView);
                hourlyWeatherAdapter = new HourlyWeatherAdapter(hourlyWeatherList, this, false);
                hourlyRecyclerView.setAdapter(hourlyWeatherAdapter);
                linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                hourlyRecyclerView.setLayoutManager(linearLayoutManager);
                doDownload(initialLocation, false);
                item.setIcon(R.drawable.units_f);
            } else {
                fahrenheit = true;
                hourlyRecyclerView = findViewById(R.id.hourlyForecastRecyclerView);
                hourlyWeatherAdapter = new HourlyWeatherAdapter(hourlyWeatherList, this, true);
                hourlyRecyclerView.setAdapter(hourlyWeatherAdapter);
                linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                hourlyRecyclerView.setLayoutManager(linearLayoutManager);
                doDownload(initialLocation, true);
                item.setIcon(R.drawable.units_c);
            }
            return true;

        } else if (item.getItemId() == R.id.showDailyForecast) {
            Intent dailyForecastIntent = new Intent(this, DailyForecastActivity.class);
            dailyForecastIntent.putExtra("location", initialLocation);
            startActivity(dailyForecastIntent);
            return true;

        } else if (item.getItemId() == R.id.changeLocation) {
            AlertDialog.Builder adBuilder = new AlertDialog.Builder(this);
            adBuilder.setTitle("Enter a Location");
            adBuilder.setMessage("For US location, enter as 'City', or 'City, State'\n\n" +
                    "For international locations enter as 'City, Country'");
            final EditText changeLocation = new EditText(this);
            adBuilder.setView(changeLocation);
            adBuilder.setPositiveButton("OK", (dialogInterface, i) -> {
                newLocation = changeLocation.getText().toString();
                doDownload(newLocation, fahrenheit);
            });
            adBuilder.setNegativeButton("Cancel", (dialogInterface, i) -> {
            });
            AlertDialog dialog = adBuilder.create();
            dialog.show();
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void updateHomeScreen(MainWeather mainWeather) {

        initialLocation = newLocation;
        this.setTitle(initialLocation);

        TextView dateTime = findViewById(R.id.currDateTime);
        dateTime.setText(mainWeather.getDateTime());

        TextView temp = findViewById(R.id.mainTemp);
        temp.setText(mainWeather.getTemp(fahrenheit));

        TextView feelsLike = findViewById(R.id.feelsLikeTemp);
        feelsLike.setText(mainWeather.getFeelsLike(fahrenheit));

        TextView humidity = findViewById(R.id.humidityView);
        humidity.setText(mainWeather.getHumidity());

        TextView uvIndex = findViewById(R.id.uvIndexView);
        uvIndex.setText(mainWeather.getUVIndex());

        TextView weatherDescription = findViewById(R.id.weatherDescription);
        weatherDescription.setText(mainWeather.getWeatherDescription());

        TextView windsDescription = findViewById(R.id.windsDescription);
        windsDescription.setText(mainWeather.getWindsDescription(fahrenheit));

        TextView visibility = findViewById(R.id.visibilityView);
        visibility.setText(mainWeather.getVisibility(fahrenheit));

        TextView morningTemp = findViewById(R.id.morningTemp);
        morningTemp.setText(mainWeather.getMorningTemp(fahrenheit));

        TextView afternoonTemp = findViewById(R.id.afternoonTemp);
        afternoonTemp.setText(mainWeather.getAfternoonTemp(fahrenheit));

        TextView eveningTemp = findViewById(R.id.eveningTemp);
        eveningTemp.setText(mainWeather.getEveningTemp(fahrenheit));

        TextView nightTemp = findViewById(R.id.nightTemp);
        nightTemp.setText(mainWeather.getNightTemp(fahrenheit));

        TextView sunrise = findViewById(R.id.sunriseView);
        sunrise.setText(mainWeather.getSunrise());

        TextView sunset = findViewById(R.id.sunsetView);
        sunset.setText(mainWeather.getSunset());

        ImageView mainWeatherIcon = findViewById(R.id.mainWeatherIcon);
        mainWeatherIcon.setImageResource(getIconResID(mainWeather.getWeatherIconID()));
    }

    private int getIconResID(String icon) {
        icon = icon.replace("-", "_");
        return this.getResources().getIdentifier(icon, "drawable", getPackageName());
    }

    public void updateHourlyWeather(ArrayList<HourlyWeather> hwList) {
        hourlyWeatherList.addAll(hwList);
        hourlyWeatherAdapter.notifyItemInserted(hwList.size());
        hourlyWeatherAdapter.notifyItemRangeChanged(0, hwList.size());
    }

    public void downloadFailed() {
        hourlyWeatherList.clear();
        hourlyWeatherAdapter.notifyItemRangeChanged(0, hourlyWeatherList.size());
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: Start application");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: Resume application");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: Application paused");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: Application stopped");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Application terminated");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: Application restarted");
    }
}