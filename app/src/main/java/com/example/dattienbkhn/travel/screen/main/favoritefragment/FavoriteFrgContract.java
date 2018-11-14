package com.example.dattienbkhn.travel.screen.main.favoritefragment;

import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.screen.BaseViewModel;
import com.example.dattienbkhn.travel.screen.BaseViewNavigator;

/**
 * Created by dattienbkhn on 09/02/2018.
 */

public interface FavoriteFrgContract {
    interface ViewModel extends BaseViewModel {
        void loadPlaceSave();

        void deletePlaceSave(int placeId);

    }

    interface View extends BaseViewNavigator {
        void goToPlaceDetailsAct(Place obj);
    }
}
