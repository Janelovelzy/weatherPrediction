package com.example.weatherprediction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.weatherprediction.gson.Weather;

public class WeatherPredictionActivity extends AppCompatActivity {

    //任何活动都要重写Activity的onCreate()方法
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_prediction_layout);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //缓存数据的判断
        if (prefs.getString("weather",null) != null) {
            Intent intent = new Intent(this, WeatherActicity.class);
            startActivity(intent);
            finish();
        }
    }
}
