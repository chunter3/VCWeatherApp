package com.example.vcweatherapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HourlyWeatherHolder extends RecyclerView.ViewHolder {

    TextView hourlyDay;
    TextView hourlyTime;
    TextView hourlyTemp;
    TextView hourlyDescription;
    ImageView hourlyIcon;

    public HourlyWeatherHolder(@NonNull View itemView) {
        super(itemView);
        hourlyDay = itemView.findViewById(R.id.hourlyWeatherDay);
        hourlyTime = itemView.findViewById(R.id.hourlyWeatherTime);
        hourlyTemp = itemView.findViewById(R.id.hourlyWeatherTemp);
        hourlyDescription = itemView.findViewById(R.id.hourlyWeatherDescription);
        hourlyIcon = itemView.findViewById(R.id.hourlyWeatherIcon);
    }
}
