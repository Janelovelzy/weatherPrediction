package com.example.weatherprediction.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by janelove on 2019/10/1.
 */

public class Now {
    //如果对象名和api中返回数据的名字相同就不用使用@SerializedName来建立映射关系
    @SerializedName("tmp")
    public String temperature;

    @SerializedName("cond")
    public More more;

    public class More {
        @SerializedName("txt")
        public String info;
    }
}
