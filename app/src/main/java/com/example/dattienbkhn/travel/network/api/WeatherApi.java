package com.example.dattienbkhn.travel.network.api;

import com.example.dattienbkhn.travel.model.weather.currentweather.WeatherObj;
import com.example.dattienbkhn.travel.model.weather.forecast.ForecastObj;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by dattienbkhn on 06/02/2018.
 */

public interface WeatherApi {
    @GET("weather?appid=f8283bfbe5e8f5f18832ad1850fb2233&units=metric")
    Observable<WeatherObj> getWeather(@Query("q") String cityName);

    @GET("forecast?appid=38894ae18a7515816112b4b343e5eccc&units=metric")
    Observable<ForecastObj> getForecast(@Query("q") String cityName);
}
