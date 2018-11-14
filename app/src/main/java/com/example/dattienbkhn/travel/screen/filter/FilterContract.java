package com.example.dattienbkhn.travel.screen.filter;

import com.example.dattienbkhn.travel.screen.BaseViewModel;
import com.example.dattienbkhn.travel.screen.BaseViewNavigator;

/**
 * Created by dattienbkhn on 02/03/2018.
 */

public interface FilterContract {
    interface ViewModel extends BaseViewModel {
        void onBackClick();

        void onSaveSetting();

        void onSeeTypeCheck();

        void onFoodiesTypeCheck();

        void onDrinkTypeCheck();

        void onStayTypeCheck();

        void loadUserSetting();
    }

    interface View extends BaseViewNavigator {

        void screenFinish ();
    }
}
