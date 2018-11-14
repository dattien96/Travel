package com.example.dattienbkhn.travel.screen.lovedplace;

import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.screen.BaseViewModel;
import com.example.dattienbkhn.travel.screen.BaseViewNavigator;

/**
 * Created by tiendatbkhn on 05/04/2018.
 */

public interface LovedPlaceContract {
    interface ViewModel extends BaseViewModel {
        void getLovedPlace(int accId);

        void onBackClick();
    }

    interface View extends BaseViewNavigator {
        void goFinish();

        void goToPlaceDetailsAct(Place obj);
    }
}

