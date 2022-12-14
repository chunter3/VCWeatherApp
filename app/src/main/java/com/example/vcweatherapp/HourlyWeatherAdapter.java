package com.example.vcweatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HourlyWeatherAdapter extends RecyclerView.Adapter<HourlyWeatherHolder> {

    private final List<HourlyWeather> weatherList;
    private final MainActivity mainAct;
    private boolean fahrenheit;

    public HourlyWeatherAdapter(List<HourlyWeather> weatherLst, MainActivity ma, boolean fahrenheit) {
        this.weatherList = weatherLst;
        mainAct = ma;
        this.fahrenheit = fahrenheit;
    }

    @NonNull
    @Override
    public HourlyWeatherHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View hourlyView = LayoutInflater.from(parent.getContext()).inflate(R.layout.hourly_weather_entry, parent, false);

        return new HourlyWeatherHolder(hourlyView);
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyWeatherHolder holder, int position) {
        HourlyWeather weather = weatherList.get(position);

        holder.hourlyDay.setText(weather.getDay());
        holder.hourlyTime.setText(weather.getTime());
        holder.hourlyTemp.setText(weather.getTemp(fahrenheit));
        holder.hourlyDescription.setText(weather.getDescription());
        holder.hourlyIcon.setImageResource(getIconResID(weather.getHourlyIconID(), mainAct));
    }

    private int getIconResID(String icon, Context context) {
        icon = icon.replace("-", "_");
        return context.getResources().getIdentifier(icon, "drawable", context.getPackageName());
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }


}
