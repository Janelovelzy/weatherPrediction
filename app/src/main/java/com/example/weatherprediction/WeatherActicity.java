package com.example.weatherprediction;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.ScrollingView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.weatherprediction.gson.Forecast;
import com.example.weatherprediction.gson.Lifestyle;
import com.example.weatherprediction.gson.Present;
import com.example.weatherprediction.gson.Suggestion;
import com.example.weatherprediction.gson.Weather;
import com.example.weatherprediction.util.HttpUtil;
import com.example.weatherprediction.util.Utility;

import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.example.weatherprediction.R.layout.forecast;

public class WeatherActicity extends AppCompatActivity {

    public SwipeRefreshLayout swipeRefresh;
    public DrawerLayout drawerLayout;

    private ScrollView weatherLayout;
    private TextView titleCity;
    private TextView titleUpdateTime;
    private TextView degreeText;
    private TextView weatherInfoText;
    private LinearLayout forecastLayout;
    private TextView windpowerText;
    private TextView windspeedText;
    private TextView comfortText;
    private TextView carWashText;
    private TextView sportText;
    private String mWeatherId;
    private ImageView bingPicImg;
    private Button navButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        if(Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            //设置活动的布局会显示在状态栏的上面，实现背景栏和状态栏融合在一起的效果
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        */
        setContentView(R.layout.weather_acticity_layout);
        //初始化各控件
        bingPicImg = (ImageView) findViewById(R.id.bing_pic_img);
        navButton = (Button) findViewById(R.id.nav_button);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        weatherLayout = (ScrollView) findViewById(R.id.weather_layout);
        titleCity = (TextView) findViewById(R.id.title_city);
        titleUpdateTime = (TextView) findViewById(R.id.title_update_time);
        degreeText = (TextView) findViewById(R.id.degree_text);
        weatherInfoText = (TextView) findViewById(R.id.weather_info_layout);
        forecastLayout = (LinearLayout) findViewById(R.id.forecast_layout);
        windpowerText = (TextView) findViewById(R.id.windpower_text);
        windspeedText = (TextView) findViewById(R.id.windspeed_text);
        comfortText = (TextView) findViewById(R.id.comfort_text);
        carWashText = (TextView) findViewById(R.id.car_wash_text);
        sportText = (TextView) findViewById(R.id.sport_text);

        navButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //调用该方法可以打开滑动菜单
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        //获取缓存数据
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = prefs.getString("weather",null);
        //String forecastString = prefs.getString("forecast",null);
        String lifestyleString = prefs.getString("lifesytle",null);
        String bingPic = prefs.getString("bing_pic",null);

        //下拉控件
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);

        if(bingPic != null) {
            Glide.with(this).load(bingPic).into(bingPicImg);
        }
        else {
            loadBingPic();
        }
        if (weatherString != null && lifestyleString != null) {
            //有缓存时直接解析天气数据
            Present weather = Utility.handleWeatherResponse(weatherString);
            //Weather forecast = Utility.handleForecastResponse(forecastString);
            Lifestyle lifestyle = Utility.handleLifestyleResponse(lifestyleString);

            showWeatherInfo(weather);
            //showForecastInfo(forecast);
            showLifestyleInfo(lifestyle);

            mWeatherId = weather.basic.weatherId;
        } else {
            //无缓存时从服务器查询天气
            mWeatherId = getIntent().getStringExtra("weather_id");
            String weatherId = getIntent().getStringExtra("weather_id");
            //请求数据的时候将ScollView进行隐藏，使得界面看起来正常
            weatherLayout.setVisibility(View.INVISIBLE);
            requestWeather(mWeatherId);
        }
        //创建一个下拉事件
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                requestWeather(mWeatherId);
            }
        });
    }

    public void requestWeather(final String weatherId) {
        Log.d("weatherId",weatherId);
        String weatherUrl = "https://free-api.heweather.net/s6/weather/now?location=" + weatherId + "&key=629146f6d7714ab6800e9510722f71f4";
        String weatherUrl2 = "https://free-api.heweather.net/s6/weather/forecast?location=" + weatherId + "&key=629146f6d7714ab6800e9510722f71f4";
        String weatherUrl3 = "https://free-api.heweather.net/s6/weather/lifestyle?location=" + weatherId + "&key=629146f6d7714ab6800e9510722f71f4";
        Log.d("nowUrl",weatherUrl);
        Log.d("forecastUrl",weatherUrl2);
        Log.d("lifestyleUrl",weatherUrl3);
        HttpUtil.sendOKHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActicity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        //关闭进度条
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                Log.d("nowData",responseText);
                //处理JSON数据
                //final Present weather = Utility.handleWeatherResponse(responseText);
                final Present weather = Utility.handleWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && "ok".equals(weather.status)) {
                            //返回的数据缓存到SharedPreferences中
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActicity.this).edit();
                            editor.putString("weather",responseText);
                            editor.apply();
                            showWeatherInfo(weather);
                        } else {
                            Toast.makeText(WeatherActicity.this,"获取天气信息失败",Toast.LENGTH_SHORT).show();
                        }
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        });
        /*
        HttpUtil.sendOKHttpRequest(weatherUr2, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActicity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        //关闭进度条
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                Log.d("forecast",responseText);
                //处理JSON数据
                final Weather forecast = Utility.handleForecastResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (forecast != null && "ok".equals(forecast.status)) {
                            //返回的数据缓存到SharedPreferences中
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActicity.this).edit();
                            editor.putString("forecast",responseText);
                            editor.apply();
                            showForecastInfo(forecast);
                        } else {
                            Toast.makeText(WeatherActicity.this,"获取天气信息失败",Toast.LENGTH_SHORT).show();
                        }
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        });
        */

        HttpUtil.sendOKHttpRequest(weatherUrl3, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActicity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        //关闭进度条
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                Log.d("lifestyleData",responseText);
                //处理JSON数据
                final Lifestyle lifestyle = Utility.handleLifestyleResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (lifestyle != null && "ok".equals(lifestyle.status)) {
                            //返回的数据缓存到SharedPreferences中
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActicity.this).edit();
                            editor.putString("lifestyle",responseText);
                            editor.apply();
                            showLifestyleInfo(lifestyle);
                        } else {
                            Toast.makeText(WeatherActicity.this,"获取天气信息失败",Toast.LENGTH_SHORT).show();
                        }
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        });
        loadBingPic();
    }

    //处理并展示Weather实体类中的数据
    private void showWeatherInfo(Present weather) {
        //Log.d("showing weather",);
        String cityName = weather.basic.cityName;
        String updateTime = weather.update.updateTime.split(" ")[1];
        String degree = weather.now.temperature + "℃";
        String weatherInfo = weather.now.info;
        titleCity.setText(cityName);
        titleUpdateTime.setText(updateTime);
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);
        /*
        forecastLayout.removeAllViews();
        for (Forecast forecast : weather.forecastList) {
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false);
            TextView dateText = (TextView) view.findViewById(R.id.date_text);
            TextView infoText = (TextView) view.findViewById(R.id.info_text);
            TextView maxText = (TextView) view.findViewById(R.id.max_text);
            TextView minText = (TextView) view.findViewById(R.id.min_text);
            dateText.setText(forecast.date);
            infoText.setText(forecast.cond_txt_d);
            maxText.setText(forecast.max);
            minText.setText(forecast.min);
            forecastLayout.addView(view);
        }
        */
        windpowerText.setText(weather.now.wind_sc);
        windspeedText.setText(weather.now.wind_spd);

        /*
        String comfort = "";
        String carWash = "";
        String sport = "";
        for (Suggestion.Content body:weather.suggestion.lifestyle) {
            if (body.type == "comf") {
                comfort = "舒适度：" + body.txt;
            }
            if (body.type == "cw" ) {
                carWash = "洗车指数：" + body.txt;
            }
            if (body.type == "sport") {
                sport = "运动建议：" + body.txt;
            }
        }
        comfortText.setText(comfort);
        carWashText.setText(carWash);
        sportText.setText(sport);
        weatherLayout.setVisibility(View.VISIBLE);
        */
    }

    private void showForecastInfo(Weather weather) {
        //forecastLayout.removeAllViews();
        for (Forecast forecast : weather.forecastList) {
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false);
            TextView dateText = (TextView) view.findViewById(R.id.date_text);
            TextView infoText = (TextView) view.findViewById(R.id.info_text);
            TextView maxText = (TextView) view.findViewById(R.id.max_text);
            TextView minText = (TextView) view.findViewById(R.id.min_text);
            dateText.setText(forecast.date);
            infoText.setText(forecast.cond_txt_d);
            maxText.setText(forecast.max);
            minText.setText(forecast.min);
            forecastLayout.addView(view);
        }
        /*
        if (weather.aqi != null) {
            windpowerText.setText(weather.now.wind_sc);
            windrectionText.setText(weather.now.wind_dir);
        }

        String comfort = "";
        String carWash = "";
        String sport = "";
        for (Suggestion body:Lifestyle.suggestionList) {
            if (body.type == "comf") {
                comfort = "舒适度：" + body.txt;
            }
            if (body.type == "cw" ) {
                carWash = "洗车指数：" + body.txt;
            }
            if (body.type == "sport") {
                sport = "运动建议：" + body.txt;
            }
        }
        comfortText.setText(comfort);
        carWashText.setText(carWash);
        sportText.setText(sport);
        weatherLayout.setVisibility(View.VISIBLE);
        */
    }

    private void showLifestyleInfo(Lifestyle lifestyle) {

        String comfort = "";
        String carWash = "";
        String sport = "";
        for (Suggestion body : lifestyle.suggestionList) {
            if (body.type.equals("comf")) {
                comfort = "舒适度：" + body.txt;
            }
            if (body.type.equals("cw")) {
                carWash = "洗车指数：" + body.txt;
            }
            if (body.type.equals("sport")) {
                sport = "运动建议：" + body.txt;
            }
        }
        comfortText.setText(comfort);
        carWashText.setText(carWash);
        sportText.setText(sport);
        weatherLayout.setVisibility(View.VISIBLE);

    }

    private void loadBingPic() {
        String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOKHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActicity.this).edit();
                editor.putString("bing_pic",bingPic);
                editor.apply();
                //将当前线程切换到主线程
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActicity.this).load(bingPic).into(bingPicImg);
                    }
                });
            }
        });
    }
}