package com.example.vcweatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DailyForecastAdapter extends RecyclerView.Adapter<DailyForecastHolder> {

    private final List<DailyWeather> dailyWeatherList;
    private final DailyForecastActivity dfAct;
    private boolean fahrenheit;


    DailyForecastAdapter(List<DailyWeather> dwLst, DailyForecastActivity dfa, boolean fahrenheit) {
        this.dailyWeatherList = dwLst;
        dfAct = dfa;
        this.fahrenheit = fahrenheit;
    }

    @NonNull
    @Override
    public DailyForecastHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_weather_entry, parent,false);

        return new DailyForecastHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyForecastHolder holder, int position) {
        DailyWeather dailyWeather = dailyWeatherList.get(position);

        holder.dayDate.setText(dailyWeather.getDayDate());
        holder.highTemp.setText(dailyWeather.getHighTemp(fahrenheit));
        holder.lowTemp.setText(dailyWeather.getLowTemp(fahrenheit));
        holder.description.setText(dailyWeather.getDescription());
        holder.precipProb.setText(dailyWeather.getPrecipProb());
        holder.uvIndex.setText(dailyWeather.getUVIndex());
        holder.morningTemp.setText(dailyWeather.getMorningTemp(fahrenheit));
        holder.afternoonTemp.setText(dailyWeather.getAfternoonTemp(fahrenheit));
        holder.eveningTemp.setText(dailyWeather.getEveningTemp(fahrenheit));
        holder.nightTemp.setText(dailyWeather.getNightTemp(fahrenheit));
        holder.dailyIcon.setImageResource(getIconResID(dailyWeather.getDailyIcon(), dfAct));
    }

    private int getIconResID(String icon, Context context) {
        icon = icon.replace("-", "_");
        return context.getResources().getIdentifier(icon, "drawable", context.getPackageName());
    }

    @Override
    public int getItemCount() {
        return dailyWeatherList.size();
    }
}
