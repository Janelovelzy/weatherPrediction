package com.example.weatherprediction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class WeatherPredictionActivity extends AppCompatActivity {

    //任何活动都要重写Activity的onCreate()方法
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_prediction_layout);
    }
}
