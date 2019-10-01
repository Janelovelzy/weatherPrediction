package com.example.weatherprediction.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by janelove on 2019/10/1.
 */

public class Weather {
    //这里创建了一个总的实体类来引用刚刚创建的各个实体类
    //status字段用来判断返回是否正常
    public String status;
    public Basic basic;
    public AQI aqi;
    public Now now;
    public Suggestion suggestion;
    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;
}
