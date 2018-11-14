package com.example.dattienbkhn.travel.screen.weather;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.util.Log;

import com.example.dattienbkhn.travel.BR;
import com.example.dattienbkhn.travel.model.weather.forecast.ForecastWrap;
import com.example.dattienbkhn.travel.model.weather.forecast.Weather;

/**
 * Created by dattienbkhn on 07/03/2018.
 */

public class ItemForecastViewModel extends BaseObservable {
    private ForecastWrap forecastWrap;
    private Weather weather;

    public final ObservableField<String> mCurrentDay = new ObservableField<>();

    public ItemForecastViewModel(ForecastWrap forecastWrap) {
        this.forecastWrap = forecastWrap;
        weather = forecastWrap.getWeather().get(0);
        mCurrentDay.set(forecastWrap.getDtTxt().substring(0,forecastWrap.getDtTxt().indexOf(" ")));

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
        notifyPropertyChanged(BR.weather);
    }
}
