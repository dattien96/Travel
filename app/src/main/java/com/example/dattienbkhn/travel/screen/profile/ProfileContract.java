package com.example.dattienbkhn.travel.screen.profile;

import android.graphics.Bitmap;

import com.example.dattienbkhn.travel.screen.BaseViewModel;
import com.example.dattienbkhn.travel.screen.BaseViewNavigator;

/**
 * Created by tiendatbkhn on 21/03/2018.
 */

public interface ProfileContract {

    interface ViewModel extends BaseViewModel {

        void setProfileImage(byte[] byteImage,boolean isAvatar);

        void onBackClick();

        void onSaveSetting();

        void onPassWdChangeClick();

        void onNameChangeClick();

        void onGenderChangeClick();

        void onBirthdayChangeClick();

        void onAddressChangeClick();

        void onAvatarChangeClick();

        void onCoverImageChangeClick();

        void loadUserInfor(int accId, String accType);

        void loadFaceBookInfor(int fbId);

        void loadGoogleInfor(int ggId);
    }

    interface View extends BaseViewNavigator {
        void goFinish();

        //if true ->image for avatar else for coverimg
        void pickImage(boolean isAvatar);
    }
}
