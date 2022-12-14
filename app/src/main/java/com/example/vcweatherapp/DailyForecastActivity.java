package com.example.vcweatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class DailyForecastActivity extends AppCompatActivity {

    private RecyclerView dailyRecyclerView;
    private DailyForecastAdapter dailyForecastAdapter;
    private LinearLayoutManager linearLayoutManager;
    private String location;
    private boolean fahrenheit = true;
    private final List<DailyWeather> dailyForecastList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);

        dailyRecyclerView = findViewById(R.id.DailyWeatherRecyclerView);
        dailyForecastAdapter = new DailyForecastAdapter(dailyForecastList,this, fahrenheit);
        dailyRecyclerView.setAdapter(dailyForecastAdapter);
        linearLayoutManager = new LinearLayoutManager(this);
        dailyRecyclerView.setLayoutManager(linearLayoutManager);

        Intent intent = getIntent();
        location = intent.getStringExtra("location");
        this.setTitle(location + " 15 Day");
        doDownload(location, fahrenheit);
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.daily_forecast_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.toggleUnitsDailyForecast) {
            if (fahrenheit) {
                fahrenheit = false;
                dailyRecyclerView = findViewById(R.id.DailyWeatherRecyclerView);
                dailyForecastAdapter = new DailyForecastAdapter(dailyForecastList,this, false);
                dailyRecyclerView.setAdapter(dailyForecastAdapter);
                linearLayoutManager = new LinearLayoutManager(this);
                dailyRecyclerView.setLayoutManager(linearLayoutManager);
                doDownload(location, false);
                item.setIcon(R.drawable.units_f);
            } else {
                fahrenheit = true;
                dailyRecyclerView = findViewById(R.id.DailyWeatherRecyclerView);
                dailyForecastAdapter = new DailyForecastAdapter(dailyForecastList,this, true);
                dailyRecyclerView.setAdapter(dailyForecastAdapter);
                linearLayoutManager = new LinearLayoutManager(this);
                dailyRecyclerView.setLayoutManager(linearLayoutManager);
                doDownload(location, true);
                item.setIcon(R.drawable.units_c);
            }
        }
        return true;
    }

    private void doDownload(String location, boolean fahrenheit) {
        DailyWeatherVolley.downloadWeather(this, location, fahrenheit);
    }

    public void updateForecast(ArrayList<DailyWeather> dwList) {
        dailyForecastList.addAll(dwList);
        dailyForecastAdapter.notifyItemInserted(dwList.size());
        dailyForecastAdapter.notifyItemRangeChanged(0, dwList.size());
    }

    public void downloadFailed() {
        dailyForecastList.clear();
        dailyForecastAdapter.notifyItemRangeChanged(0, dailyForecastList.size());
    }
}