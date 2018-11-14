package com.example.dattienbkhn.travel.screen.weather;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.dattienbkhn.travel.R;
import com.example.dattienbkhn.travel.databinding.ActivityWeatherBinding;
import com.example.dattienbkhn.travel.model.weather.currentweather.WeatherObj;
import com.example.dattienbkhn.travel.model.weather.forecast.ForecastWrap;
import com.example.dattienbkhn.travel.utils.dialog.DialogManager;

import java.util.ArrayList;

public class WeatherActivity extends AppCompatActivity implements WeatherContract.View {
    public static final String WEATHER_ACT_FLAG = "WeatherActivity";
    private WeatherObj mCurrentWeatherObj;
    private WeatherViewModel mViewModel;
    private DialogManager mDialogManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityWeatherBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_weather);

        Bundle bd = getIntent().getExtras().getBundle(WEATHER_ACT_FLAG);
        mCurrentWeatherObj = (WeatherObj) bd.getSerializable("weatherObj");

        mDialogManager = new DialogManager(this);

        mViewModel = new WeatherViewModel(mDialogManager);
        mViewModel.setCurrentWeatherObj(mCurrentWeatherObj);
        mViewModel.setViewNavigator(this);
        mViewModel.setForecasts(new ArrayList<ForecastWrap>());
        mViewModel.setTodayWeather(new ArrayList<ForecastWrap>());
        mViewModel.setForecastAdapter(new ForecastAdapter(this,new ArrayList<ForecastWrap>()));
        mViewModel.setTodayWeatherAdapter(new TodayWeatherAdapter(this,new ArrayList<ForecastWrap>()));
        onInitViewModel();
        binding.setViewModel(mViewModel);
    }

    @Override
    public void onInitViewModel() {
        mViewModel.onStart();
    }

    @Override
    public void onErrorMessage(String mes) {
        Toast.makeText(this, ""+mes, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFinish() {
        finish();
    }
}
