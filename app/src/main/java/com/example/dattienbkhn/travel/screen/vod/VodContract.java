package com.example.dattienbkhn.travel.screen.vod;

import com.example.dattienbkhn.travel.screen.BaseViewModel;
import com.example.dattienbkhn.travel.screen.BaseViewNavigator;

/**
 * Created by tiendatbkhn on 29/04/2018.
 */

public interface VodContract {
    interface ViewModel extends BaseViewModel {
        void loadVod(int accId);

        void onBackClick();
    }

    interface View extends BaseViewNavigator {
        void finishView();
    }
}
