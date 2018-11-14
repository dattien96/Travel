package com.example.dattienbkhn.travel.screen.city.homeFragment;

import com.example.dattienbkhn.travel.model.app.City;
import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.model.weather.currentweather.WeatherObj;
import com.example.dattienbkhn.travel.screen.BaseViewModel;
import com.example.dattienbkhn.travel.screen.BaseViewNavigator;

/**
 * Created by dattienbkhn on 09/02/2018.
 */

public interface HomeFrgContract {
    interface ViewModel extends BaseViewModel {
        void loadImageSlideOfCity(int idCity);

        void loadFamousPlayPlace(int idCity);

        void loadFamousStayPlace(int idCity);

        void loadFamousEatDrinkPlace(int idCity);

        void loadFamousEnterPlace(int idCity);

        void loadWeatherCurrent(String cityName);

        void onSeeMoreClick();

        void onWeatherClick();
    }

    interface View extends BaseViewNavigator {
        void goToDetailsPlaceAct(Place obj);

        void setCurrentCityInstance(City obj);

        void goToExploreTab();

        void goToUtilTab();

        void goToWeatherAct(WeatherObj obj);

    }
}
