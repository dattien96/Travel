package com.example.dattienbkhn.travel.screen.friendlist;

import com.example.dattienbkhn.travel.screen.BaseViewModel;
import com.example.dattienbkhn.travel.screen.BaseViewNavigator;

import io.reactivex.Observable;

/**
 * Created by tiendatbkhn on 25/04/2018.
 */

public interface FriendListContract {
    interface ViewModel extends BaseViewModel {
        void getAllFriend(int accId);

        void onBackClick();

        Observable<String> listenSearch();

        void loadResultForSearch();

        void findFriends(String s);
    }

    interface View extends BaseViewNavigator {
        void goToFriendAct(int accId);

        void finishView();
    }
}
