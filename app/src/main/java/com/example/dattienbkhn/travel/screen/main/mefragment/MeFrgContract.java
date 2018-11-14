package com.example.dattienbkhn.travel.screen.main.mefragment;

import com.example.dattienbkhn.travel.screen.BaseViewModel;
import com.example.dattienbkhn.travel.screen.BaseViewNavigator;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;

/**
 * Created by dattienbkhn on 09/02/2018.
 */

public interface MeFrgContract {

    interface ViewModel extends BaseViewModel {
        void onSignUpClick();

        void onSettingClick();

        boolean checkLogin();

        void onFacebookLogin();

        void initFaceBook();

        void handleGoogleSignInResult(Task<GoogleSignInAccount> completedTask);

        void onGoogleLogin();

        void loadCoverFbImage(String userId, String token);

        void saveFaceBookAccount(String userName);

        void saveGoogleAccount(String userName);

        void getUserInfor();

        void getTopFriends(int accId);

        void onLovedPlaceClick();

        void onStreamClick();

        void onFriendSearchClick();

        void onVodClick();

        void onViewMoreFriendsClick();

        void getStreamState(int accId);

    }

    interface View extends BaseViewNavigator {

        void goToRegisterAct();

        void goToSettingAct();

        void loginGoogle();

        void goToFriendAct(int accId);

        void goToProfileAct(int accId,String accType);

        void goToLovedPlaceAct(int accId);

        void goToLiveStreamAct(int accId);

        void connectSocket();

        void goToFriendListAct(int accId);

        void goToVodAct(int accId,String accName);

    }
}
