package com.example.dattienbkhn.travel.network.service;

import android.app.Application;
import android.support.annotation.NonNull;

import com.example.dattienbkhn.travel.network.RetrofitHelper;
import com.example.dattienbkhn.travel.network.api.WeatherApi;
import com.example.dattienbkhn.travel.utils.Constant;

/**
 * Created by dattienbkhn on 06/02/2018.
 */

public class WeatherService {
    private static WeatherApi mWeatherApi;

    public static void initWeatherService(@NonNull Application app) {
        // SharedPrefsApi prefsApi = SharedPrefsImpl.getInstance();
        // String token = prefsApi.get(SharedPrefsKey.TOKEN_KEY, String.class);
        //RetrofitInterceptor interceptor = null;
        // if (!TextUtils.isEmpty(token)) {
        //    interceptor = new RetrofitInterceptor(token);
        //}
        mWeatherApi = RetrofitHelper.createService(app, Constant.WEATHER_API_BASE_URL, WeatherApi.class);
    }

    public static WeatherApi getWeatherApiInstance(){
        if (mWeatherApi == null) {
            throw new RuntimeException("Need call method FOrderServiceClient#initialize() first");
        }
        return mWeatherApi;
    }
}
