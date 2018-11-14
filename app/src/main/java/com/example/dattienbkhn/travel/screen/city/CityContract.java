package com.example.dattienbkhn.travel.screen.city;

import com.example.dattienbkhn.travel.screen.BaseViewModel;
import com.example.dattienbkhn.travel.screen.BaseViewNavigator;

/**
 * Created by dattienbkhn on 09/02/2018.
 */

public interface CityContract {
    interface ViewModel extends BaseViewModel {
        void onBackClick();

        void onSearchClick();

        void increaseCityViews(int cityId);
    }

    interface View extends BaseViewNavigator {

        void goBack();

        void goSearchAct();

    }
}
