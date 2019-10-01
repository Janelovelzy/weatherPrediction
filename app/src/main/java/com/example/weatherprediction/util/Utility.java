package com.example.weatherprediction.util;

import android.content.ContentUris;
import android.text.TextUtils;

import com.example.weatherprediction.db.City;
import com.example.weatherprediction.db.County;
import com.example.weatherprediction.db.Province;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by janelove on 2019/9/28.
 */

public class Utility {
    //解析和处理服务器返回的省级数据
    public static boolean handleProvinceResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                if (response != null && response.startsWith("\ufeff")) {
                    response = response.substring(1);
                }
                JSONArray allProvinces = new JSONArray(response);
                for (int i = 0; i < allProvinces.length(); ++i) {
                    JSONObject provinceObject = allProvinces.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    province.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    //解析和处理服务器返回的市级数据
    public static boolean handleCityResponse(String response,int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                if (response != null && response.startsWith("\ufeff")) {
                    response = response.substring(1);
                }
                JSONArray allCities = new JSONArray(response);
                for (int i = 0; i < allCities.length(); ++i) {
                    JSONObject cityObject = allCities.getJSONObject(i);
                    City city = new City();
                    city.setcityName(cityObject.getString("name"));
                    city.setcityCode(cityObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    //解析和处理服务器返回的县级数据
    public static boolean handleCountyResponse(String response,int cityId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                if (response != null && response.startsWith("\ufeff")) {
                    response = response.substring(1);
                }
                //将整个JSON实例化保存在jsonObject中
                JSONArray allcounties = new JSONArray(response);
                for (int i = 0; i < allcounties.length(); ++i) {
                    JSONObject countyObject = allcounties.getJSONObject(i);
                    County county = new County();
                    county.setcountyName(countyObject.getString("name"));
                    county.setWeatherId(countyObject.getString("weather_id"));
                    county.setcityId(cityId);
                    county.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
