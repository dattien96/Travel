package com.example.dattienbkhn.travel.screen.weather;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.dattienbkhn.travel.BR;
import com.example.dattienbkhn.travel.model.weather.forecast.ForecastWrap;
import com.example.dattienbkhn.travel.model.weather.forecast.Weather;

/**
 * Created by dattienbkhn on 08/03/2018.
 */

public class ItemTodayWeatherViewModel extends BaseObservable{
    private ForecastWrap forecastWrap;
    private Weather weather;

    public ItemTodayWeatherViewModel(ForecastWrap forecastWrap) {
        this.forecastWrap = forecastWrap;
        weather = forecastWrap.getWeather().get(0);

    }

    @Bindable
    public ForecastWrap getForecastWrap() {
        return forecastWrap;
    }

    public void setForecastWrap(ForecastWrap forecastWrap) {
        this.forecastWrap = forecastWrap;

    }

    @Bindable
    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }
}
