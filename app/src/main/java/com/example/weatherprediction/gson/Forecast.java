package com.example.weatherprediction.gson;

import com.google.gson.annotations.SerializedName;

import javax.xml.transform.Templates;

/**
 * Created by janelove on 2019/10/1.
 */

public class Forecast {
    //在返回的数据daily_forecast中包含一个数组，和上述形式不太一样
    //针对这种情况，可以只定义出单日天气的实体类，然后在声明实体类引用的时候使用集合类型来进行声明
    public String date;

    @SerializedName("tmp_max")
    public String max;

    @SerializedName("tmp_min")
    public String min;

    @SerializedName("cond_txt_d")
    public String cond_txt_d;
}
