package com.example.dattienbkhn.travel.screen.city.utilFragment;

import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.model.app.PlaceInfor;
import com.example.dattienbkhn.travel.screen.BaseViewModel;
import com.example.dattienbkhn.travel.screen.BaseViewNavigator;

/**
 * Created by dattienbkhn on 10/02/2018.
 */

public interface UtilFrgContract {
    interface ViewModel extends BaseViewModel {
        void loadNearByPlace(String type, String location);

        void onTypeRestaurantClick();

        void onPlaceTypeHotelClick();

        void onPlaceTypeHospitalClick();

        void onPlaceTypeAtmClick();

        void onPlaceTypeGasStationClick();

        void onPlaceTypePoliceClick();

        void onPlaceInforClick();

        void updateStateLocation(boolean state);

        void onTurnOnLocationClick();
    }

    interface View extends BaseViewNavigator {
        void goToPlaceDetailsAct(Place obj);

        void goToMapAct(Place obj, PlaceInfor placeInfor);

        void checkStateLocation();

        void goToAppSetting();

    }
}
