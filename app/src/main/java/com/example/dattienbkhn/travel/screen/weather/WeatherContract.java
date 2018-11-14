package com.example.dattienbkhn.travel.screen.weather;

import com.example.dattienbkhn.travel.screen.BaseViewModel;
import com.example.dattienbkhn.travel.screen.BaseViewNavigator;

/**
 * Created by dattienbkhn on 07/03/2018.
 */

public interface WeatherContract {
    interface ViewModel extends BaseViewModel {
        void onbackClick();

        void getForeCast(String cityName);
    }

    interface View extends BaseViewNavigator {
        void onFinish();
    }
}
