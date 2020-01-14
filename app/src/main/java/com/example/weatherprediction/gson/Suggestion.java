package com.example.weatherprediction.gson;

import android.widget.Space;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by janelove on 2019/10/1.
 */

public class Suggestion {

        @SerializedName("type")
        public String type;
        @SerializedName("brf")
        public String brf;
        @SerializedName("txt")
        public String txt;

    /*
    @SerializedName("comf")
    public Comfort comfort;

    @SerializedName("cw")
    public CarWash carWash;

    public Sport sport;

    public class Comfort {
        @SerializedName("txt")
        public String info;
    }

    public class CarWash {
        @SerializedName("txt")
        public String info;
    }

    public class Sport {
        @SerializedName("txt")
        public String info;
    }
    */
}
