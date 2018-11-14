package com.example.dattienbkhn.travel.screen.friend;

import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.screen.BaseViewModel;
import com.example.dattienbkhn.travel.screen.BaseViewNavigator;

/**
 * Created by tiendatbkhn on 02/04/2018.
 */

public interface FriendContract {

    interface ViewModel extends BaseViewModel {

        void getFriendInfor(int friendId);

        void getLovedPlace(int friendId);

        void checkFollow(int accId, int friendId);

        void getStreamState(int friendId);

        void follow(int accId, int friendId,boolean isFollow);

        void playLiveStream(int friendId);

        void onBackClick();

        void onFollowClick();

        void onStreamPlayClick();

        void onVodClick();

        void onStop();

        void updateStreamState(boolean streamState);
    }

    interface View extends BaseViewNavigator {

        void goFinish();

        void goToPlaceDetailsAct(Place obj);

        void goToPlayStreamAct(int friendId);

        void goToVodAct(int accId,String accName);
    }
}
