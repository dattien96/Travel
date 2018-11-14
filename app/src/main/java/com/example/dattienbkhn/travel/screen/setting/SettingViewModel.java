package com.example.dattienbkhn.travel.screen.setting;

import android.content.SharedPreferences;
import android.databinding.BaseObservable;

import com.example.dattienbkhn.travel.database.sharedPreference.TravelSharedPreference;
import com.example.dattienbkhn.travel.databinding.ActivitySettingBinding;
import com.example.dattienbkhn.travel.screen.BaseViewNavigator;
import com.example.dattienbkhn.travel.screen.stream.SocketConfig;
import com.example.dattienbkhn.travel.utils.Constant;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;

/**
 * Created by dattienbkhn on 09/03/2018.
 */

public class SettingViewModel extends BaseObservable implements SettingContract.ViewModel {
    private SettingContract.View mNavigator;
    private ActivitySettingBinding binding;
    private String loginType;

    @Override
    public void onStart() {
        checkLoginType();
        initFacebook();
    }

    @Override
    public void initFacebook() {
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null) {

                    //save login state
                    SharedPreferences prefs = TravelSharedPreference.getINSTANCE();
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean(Constant.SHARED_LOGIN_STATE, false);
                    editor.putString(Constant.SHARED_FACEBOOK_TOKEN, "");
                    editor.putInt(Constant.SHARED_LOGIN_ACC_ID,-1);
                    editor.commit();

                    SocketConfig.getSocketInstance().disconnect();
                    mNavigator.goFinish();
                }
            }
        };
    }

    @Override
    public void onChangeLanguageClick() {
        mNavigator.onErrorMessage("Developing!");
    }

    @Override
    public void onNotiModifyClick() {
        mNavigator.onErrorMessage("Developing!");
    }

    @Override
    public void onGrantPerClick() {
        mNavigator.onErrorMessage("Developing!");
    }


    @Override
    public void setViewNavigator(BaseViewNavigator viewNavigator) {
        this.mNavigator = (SettingContract.View) viewNavigator;
    }

    public void setBinding(ActivitySettingBinding binding) {
        this.binding = binding;
    }

    @Override
    public void onBackClick() {
        mNavigator.goFinish();
    }

    @Override
    public void checkLoginType() {
        SharedPreferences prefs = TravelSharedPreference.getINSTANCE();
        loginType = prefs.getString(Constant.SHARED_LOGIN_TYPE, "");
    }

    @Override
    public void onLogOutClick() {
        if (loginType.equalsIgnoreCase(Constant.SHARED_LOGIN_FACEBOOK)) {
            //login with fb
            binding.loginButton.performClick();
        } else if (loginType.equalsIgnoreCase(Constant.SHARED_LOGIN_GOOGLE)) {
            //login with gg
            mNavigator.googleLogOut();
        } else {
            //login with app acc
        }
    }

    @Override
    public void onProfileClick() {
        SharedPreferences prefs = TravelSharedPreference.getINSTANCE();
        int accId = prefs.getInt(Constant.SHARED_LOGIN_ACC_ID,-1);
        String accType = prefs.getString(Constant.SHARED_LOGIN_TYPE,"");
        mNavigator.goToProfileAct(accId,accType);
    }
}
