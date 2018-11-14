package com.example.dattienbkhn.travel.screen.setting;

import com.example.dattienbkhn.travel.screen.BaseViewModel;
import com.example.dattienbkhn.travel.screen.BaseViewNavigator;

/**
 * Created by dattienbkhn on 09/03/2018.
 */

public interface SettingContract {
    interface ViewModel extends BaseViewModel {
        void onBackClick();

        void checkLoginType();

        void onLogOutClick();

        void onProfileClick();

        void initFacebook();

        void onChangeLanguageClick();

        void onNotiModifyClick();

        void onGrantPerClick();


    }

    interface View extends BaseViewNavigator {
        void goFinish();

        void googleLogOut();

        void appAccLogOut();

        void goToProfileAct(int accId,String accType);
    }
}
