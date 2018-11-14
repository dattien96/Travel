package com.example.dattienbkhn.travel.screen.main.mainfragment;

import com.example.dattienbkhn.travel.model.app.City;
import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.screen.BaseViewModel;
import com.example.dattienbkhn.travel.screen.BaseViewNavigator;

/**
 * Created by dattienbkhn on 07/02/2018.
 */

public interface MainFrgContract {
    interface ViewModel extends BaseViewModel {
        /**
         * Method load image collapse in main
         */
        void loadCollapseImage();

        /**
         * Event search click
         */
        void onSearchClick();

        /**
         * Load top famous place for curren country
         */
        void loadFamousPlaceOfCity();

        void loadTopCities();

        void saveUserLocation (String cityName);

    }

    interface View extends BaseViewNavigator {
        void goToPlaceDetailsAct(Place obj);
        void goToCityAct(City obj);
        void goToSearchAct();
    }
}
