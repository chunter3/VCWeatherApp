package com.example.vcweatherapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DailyForecastHolder extends RecyclerView.ViewHolder {

    TextView dayDate;
    TextView highTemp;
    TextView lowTemp;
    TextView description;
    TextView precipProb;
    TextView uvIndex;
    TextView morningTemp;
    TextView afternoonTemp;
    TextView eveningTemp;
    TextView nightTemp;
    ImageView dailyIcon;


    public DailyForecastHolder(@NonNull View itemView) {
        super(itemView);
        dayDate = itemView.findViewById(R.id.dailyDayDate);
        highTemp = itemView.findViewById(R.id.highTemp);
        lowTemp = itemView.findViewById(R.id.lowTemp);
        description = itemView.findViewById(R.id.dailyDescription);
        precipProb = itemView.findViewById(R.id.dailyPrecipProb);
        uvIndex = itemView.findViewById(R.id.dailyUVIndex);
        morningTemp = itemView.findViewById(R.id.dailyMorningTemp);
        afternoonTemp = itemView.findViewById(R.id.dailyAfternoonTemp);
        eveningTemp = itemView.findViewById(R.id.dailyEveningTemp);
        nightTemp = itemView.findViewById(R.id.dailyNightTemp);
        dailyIcon = itemView.findViewById(R.id.dailyWeatherIcon);
    }
}
