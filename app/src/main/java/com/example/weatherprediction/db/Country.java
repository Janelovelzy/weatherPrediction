package com.example.weatherprediction.db;

import org.litepal.crud.DataSupport;

/**
 * Created by janelove on 2019/9/28.
 */

public class Country extends DataSupport {
    private int id;
    private String countryName;
    private String weatherId;
    private int cityId;
    public int getId() {
        return  id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getcountryName() {
        return countryName;
    }
    public void setcountryName(String countryName) {
        this.countryName = countryName;
    }
    public String getWeatherId() {
        return weatherId;
    }
    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }
    public int getcityId() {
        return cityId;
    }
    public void setcityId(int cityId) {
        this.cityId = cityId;
    }
}
