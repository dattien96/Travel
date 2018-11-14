package com.example.dattienbkhn.travel.screen.city.utilFragment.contain;

import com.example.dattienbkhn.travel.screen.BaseViewModel;
import com.example.dattienbkhn.travel.screen.BaseViewNavigator;

/**
 * Created by tiendatbkhn on 09/04/2018.
 */

public interface ContainFrgContract {

    interface ViewModel extends BaseViewModel {
        void onReloadClick();

        void updatePermission(boolean isGranded);
    }

    interface View extends BaseViewNavigator {
        void initUtilFrg();

        void checkPermission();
    }
}
