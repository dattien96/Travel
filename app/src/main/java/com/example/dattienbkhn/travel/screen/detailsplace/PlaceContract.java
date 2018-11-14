package com.example.dattienbkhn.travel.screen.detailsplace;

import com.example.dattienbkhn.travel.model.app.Love;
import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.model.app.PlaceInfor;
import com.example.dattienbkhn.travel.model.app.Rate;
import com.example.dattienbkhn.travel.screen.BaseViewModel;
import com.example.dattienbkhn.travel.screen.BaseViewNavigator;

/**
 * Created by dattienbkhn on 12/02/2018.
 */

public interface PlaceContract {
    interface ViewModel extends BaseViewModel {

        void onStop();

        void onResume();

        void reloadPlaceData(int placeId);

        void increasePlaceView(int placeId);

        void loadImageSlideOfPlace(int idPlace);

        void loadCommentOfPlace(int idPlace);

        void loadDetailsInforOfPlace(int idAddress);

        void getCityId(int placeId);

        void getUserRateOfplace(int accId, int placeId);

        void onBackClick();

        void onLovesClick();

        void onSaveClick();

        void onShareClick();

        void onReviewsClick();

        void onMapClick();

        void onRateClick();

        void onViewMoreCmtClick();

        boolean checkLogin();

        void checkSavePlace();

        void getPlaceLoveOfUser(int accId, int placeId);

        void saveLoveState(Love love, boolean isLove);

        void saveRate(Rate rate);

        void savePlace(Place place);
    }

    interface View extends BaseViewNavigator {

        void goBack();

        void goToCameraSurface();

        void goToMapAct(Place obj, PlaceInfor placeInfor);

        void goToCommentAct(int accId, Place obj);

        void goToFriendAct(int accId);

        void goToProfileAct(int accId,String accType);

        void onLocationPermissionCheck(Place obj, PlaceInfor placeInfor);

    }
}
