package com.example.weatherprediction.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by janelove on 2019/10/22.
 */

public class Lifestyle {

    @SerializedName("basic")
    public Basic basic;
    @SerializedName("update")
    public Update update ;
    @SerializedName("status")
    public String status;
    @SerializedName("lifestyle")
    public List<Suggestion> suggestionList;
}
