package com.example.weatherprediction.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by janelove on 2019/10/1.
 */

public class Basic {
    //由于JSON中的一些字段不太适合直接作为Java字段来命名
    //这里使用了@SerializedName注释的方式来让JSON字段和java之间建立映射关系
    @SerializedName("city")
    public String cityName;

    @SerializedName("id")
    public String weatherId;

    public Update update;

    public class Update {
        @SerializedName("loc")
        public String updateTime;
    }
}
