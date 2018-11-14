package com.example.dattienbkhn.travel.screen.city.utilFragment.contain;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;

import com.example.dattienbkhn.travel.screen.BaseViewNavigator;

/**
 * Created by tiendatbkhn on 09/04/2018.
 */

public class ContainFrgViewModel extends BaseObservable implements ContainFrgContract.ViewModel {
    private ContainFrgContract.View mNavigator;

    public ObservableField<Boolean> isPermissionGranded = new ObservableField<>();

    @Override
    public void onStart() {
        isPermissionGranded.set(Boolean.FALSE);
    }

    @Override
    public void setViewNavigator(BaseViewNavigator viewNavigator) {
        mNavigator = (ContainFrgContract.View) viewNavigator;
    }

    @Override
    public void onReloadClick() {
        mNavigator.checkPermission();
    }

    @Override
    public void updatePermission(boolean isGranded) {
        isPermissionGranded.set(isGranded);
    }
}
