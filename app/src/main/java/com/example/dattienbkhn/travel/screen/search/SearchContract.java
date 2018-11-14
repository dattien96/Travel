package com.example.dattienbkhn.travel.screen.search;

import com.example.dattienbkhn.travel.model.app.City;
import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.screen.BaseViewModel;
import com.example.dattienbkhn.travel.screen.BaseViewNavigator;

import io.reactivex.Observable;

/**
 * Created by dattienbkhn on 07/02/2018.
 */

public interface SearchContract {
    interface ViewModel extends BaseViewModel {
        void onBackClick();

        void loadRegionFilter();

        Observable<String> listenSearch();

        void loadResultForSearch();

        String getStringRegionChoosed();

        void findPlace(String placeName, String regionName);

        void findCity(String cityName, String regionName);


    }

    interface View extends BaseViewNavigator {
        void goFinish();

        void goToCityAct(City obj);

        void goToPlaceDetailsAct(Place obj);
    }
}
