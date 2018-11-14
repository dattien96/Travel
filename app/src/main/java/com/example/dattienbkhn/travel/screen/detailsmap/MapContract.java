package com.example.dattienbkhn.travel.screen.detailsmap;

import android.location.Location;

import com.example.dattienbkhn.travel.screen.BaseViewModel;
import com.example.dattienbkhn.travel.screen.BaseViewNavigator;

/**
 * Created by dattienbkhn on 03/03/2018.
 */

public interface MapContract {
    interface ViewModel extends BaseViewModel {
        void directPolyline(Location location);

        void loadCurrentLocationName(Location location);

        void startDetailsDirection();

        void onShowListStepClick();

        void onCameraCapture();

        void onNextDirectStep();

        void onCloseDetailsStep();

        void updateStateLocation(boolean isTurnOn);

        void onTurnOnLocationClick();
    }

    interface View extends BaseViewNavigator {
        void goToCameraAct();

        void checkStateLocation();

        void goToAppSetting();
    }
}
