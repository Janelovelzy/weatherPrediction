package com.example.weatherprediction.gson;

/**
 * Created by janelove on 2019/10/1.
 */

public class AQI {
    public AQICity city;
    public class AQICity {
        public String aqi;
        public String pm25;
    }
}
