package com.example.dattienbkhn.travel.screen.city.exploreFragment;

import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.screen.BaseViewModel;
import com.example.dattienbkhn.travel.screen.BaseViewNavigator;

/**
 * Created by dattienbkhn on 10/02/2018.
 */

public interface ExploreFrgContract {
    interface ViewModel extends BaseViewModel {
        void loadPlaceByType(int idCity);

        void loadUserFilterSetting ();

        void onFilterClick();
    }

    interface View extends BaseViewNavigator {
        void goToPlaceDetailsAct(Place obj);

        void goToFilterAct ();
    }
}
