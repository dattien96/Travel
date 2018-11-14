package com.example.dattienbkhn.travel.screen.main;

import com.example.dattienbkhn.travel.screen.BaseViewModel;
import com.example.dattienbkhn.travel.screen.BaseViewNavigator;

/**
 * Created by dattienbkhn on 08/02/2018.
 */

public interface MainContract {
    interface ViewModel extends BaseViewModel {

        void onHomeFrgClick();

        void onMeFrgClick();

        void onFavoriteFrgClick();

        void notifyNewStream(int accId,int streamState);
    }

    interface View extends BaseViewNavigator {

        void showHomeFrg();

        void showMeFrg();

        void showFavoriteFrg();

        void connectSocketIO();

        void destroySocketIO();

        void showNotificationStream(int accId,String accName);



    }
}
