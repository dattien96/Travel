package com.example.dattienbkhn.travel.screen.weather;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;

import com.example.dattienbkhn.travel.BR;
import com.example.dattienbkhn.travel.model.weather.currentweather.Weather;
import com.example.dattienbkhn.travel.model.weather.currentweather.WeatherObj;
import com.example.dattienbkhn.travel.model.weather.forecast.ForecastObj;
import com.example.dattienbkhn.travel.model.weather.forecast.ForecastWrap;
import com.example.dattienbkhn.travel.repository.AppRepository;
import com.example.dattienbkhn.travel.screen.BaseViewNavigator;
import com.example.dattienbkhn.travel.utils.dialog.DialogManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dattienbkhn on 07/03/2018.
 */

public class WeatherViewModel extends BaseObservable implements WeatherContract.ViewModel {
    private WeatherObj mCurrentWeatherObj;
    private Weather mCurrentWeather;
    private WeatherContract.View mNavigator;
    private DialogManager mDialogManager;

    private List<ForecastWrap> mForecasts;
    private ForecastAdapter mForecastAdapter;
    private List<ForecastWrap> mTodayWeather;
    private TodayWeatherAdapter mTodayWeatherAdapter;

    public WeatherViewModel(DialogManager mDialogManager) {
        this.mDialogManager = mDialogManager;
    }

    @Override
    public void onStart() {
        //Weather contain city name
        getForeCast(mCurrentWeatherObj.getName());
    }

    @Override
    public void setViewNavigator(BaseViewNavigator viewNavigator) {
        this.mNavigator = (WeatherContract.View) viewNavigator;
    }

    @Override
    public void onbackClick() {
        mNavigator.onFinish();
    }

    @Override
    public void getForeCast(String cityName) {
        AppRepository.getCommonRepo().getForecast(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mDialogManager.showProgressDialog();
                    }
                }).subscribe(new Consumer<ForecastObj>() {
            @Override
            public void accept(ForecastObj forecastObj) throws Exception {

                Date date = new Date();
                String dateFormat = new SimpleDateFormat("yyyy-MM-dd").format(date);
                List<ForecastWrap> tmp = forecastObj.getForecastWrap();
                for (int i = 0; i < tmp.size(); i++) {
                    Log.e("date"," "+tmp.get(i).getDtTxt());
                    if (tmp.get(i).getDtTxt().contains(dateFormat)) {
                        mTodayWeather.add(tmp.get(i));
                        forecastObj.getForecastWrap().remove(i);
                    }
                }

                for (int i = 0; i < forecastObj.getForecastWrap().size(); i++) {
                    if (forecastObj.getForecastWrap().get(i).getDtTxt().endsWith("06:00:00")) {
                        mForecasts.add(forecastObj.getForecastWrap().get(i));
                    }
                }

                mForecastAdapter.setData(mForecasts);
                mTodayWeatherAdapter.setData(mTodayWeather);
                mDialogManager.dismissProgressDialog();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                mDialogManager.dismissProgressDialog();
                mNavigator.onErrorMessage("Error when load forecast !");
            }
        });
    }

    @Bindable
    public WeatherObj getCurrentWeatherObj() {
        return mCurrentWeatherObj;
    }

    public void setCurrentWeatherObj(WeatherObj mCurrentWeatherObj) {
        this.mCurrentWeatherObj = mCurrentWeatherObj;
        setCurrentWeather(mCurrentWeatherObj.getWeather().get(0));
        notifyPropertyChanged(BR.currentWeatherObj);
    }

    public void setForecasts(List<ForecastWrap> mForecasts) {
        this.mForecasts = mForecasts;
    }

    public void setTodayWeather(List<ForecastWrap> mTodayWeather) {
        this.mTodayWeather = mTodayWeather;
    }

    @Bindable
    public ForecastAdapter getForecastAdapter() {
        return mForecastAdapter;
    }

    public void setForecastAdapter(ForecastAdapter mForecastAdapter) {
        this.mForecastAdapter = mForecastAdapter;
    }

    @Bindable
    public TodayWeatherAdapter getTodayWeatherAdapter() {
        return mTodayWeatherAdapter;
    }

    public void setTodayWeatherAdapter(TodayWeatherAdapter mTodayWeatherAdapter) {
        this.mTodayWeatherAdapter = mTodayWeatherAdapter;
        notifyPropertyChanged(BR.todayWeatherAdapter);
    }

    @Bindable
    public Weather getCurrentWeather() {
        return mCurrentWeather;
    }

    public void setCurrentWeather(Weather mCurrentWeather) {
        this.mCurrentWeather = mCurrentWeather;
        notifyPropertyChanged(BR.currentWeather);
    }
}
