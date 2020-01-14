package com.example.weatherprediction.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by janelove on 2019/10/1.
 */

//这个类是用来实现未来三天天气预报
public class Weather {
    //这里创建了一个总的实体类来引用刚刚创建的各个实体类
    //status字段用来判断返回是否正常
    public Basic basic;
    public Update update;
    public String status;
    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;
    /*
    */
}
