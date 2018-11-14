package com.example.dattienbkhn.travel.screen.filter;

import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;

import com.example.dattienbkhn.travel.database.sharedPreference.TravelSharedPreference;
import com.example.dattienbkhn.travel.screen.BaseViewNavigator;
import com.example.dattienbkhn.travel.utils.Constant;

/**
 * Created by dattienbkhn on 02/03/2018.
 */

public class FilterViewModel extends BaseObservable implements FilterContract.ViewModel {
    private FilterContract.View mNavigator;

    public final ObservableField<Boolean> isSeeTypeCheck = new ObservableField<>();
    public final ObservableField<Boolean> isFoodiesTypeCheck = new ObservableField<>();
    public final ObservableField<Boolean> isDrinkTypeCheck = new ObservableField<>();
    public final ObservableField<Boolean> isStayTypeCheck = new ObservableField<>();

    public FilterViewModel() {
    }

    @Override
    public void onStart() {
        isStayTypeCheck.set(false);
        isDrinkTypeCheck.set(false);
        isSeeTypeCheck.set(true);
        isFoodiesTypeCheck.set(false);
        loadUserSetting();
    }

    @Override
    public void setViewNavigator(BaseViewNavigator viewNavigator) {
        this.mNavigator = (FilterContract.View) viewNavigator;
    }

    @Override
    public void onBackClick() {
        mNavigator.screenFinish();
    }

    @Override
    public void onSaveSetting() {
        SharedPreferences prefs = TravelSharedPreference.getINSTANCE();
        SharedPreferences.Editor editor = prefs.edit();
        if (!isSeeTypeCheck.get() && !isFoodiesTypeCheck.get()
                && !isDrinkTypeCheck.get() && !isStayTypeCheck.get()) {
            editor.putBoolean(Constant.SHARED_PLACE_SEE_TYPE, true);
        } else {
            editor.putBoolean(Constant.SHARED_PLACE_SEE_TYPE, isSeeTypeCheck.get());
        }

        editor.putBoolean(Constant.SHARED_PLACE_FOODIES_TYPE, isFoodiesTypeCheck.get());
        editor.putBoolean(Constant.SHARED_PLACE_DRINK_TYPE, isDrinkTypeCheck.get());
        editor.putBoolean(Constant.SHARED_PLACE_STAY_TYPE, isStayTypeCheck.get());
        editor.apply();
        editor.commit();
        mNavigator.screenFinish();
    }

    @Override
    public void onSeeTypeCheck() {
        if (isSeeTypeCheck.get()) {
            isSeeTypeCheck.set(false);
        } else {
            isSeeTypeCheck.set(true);
        }
    }

    @Override
    public void onFoodiesTypeCheck() {
        if (isFoodiesTypeCheck.get()) {
            isFoodiesTypeCheck.set(false);
        } else {
            isFoodiesTypeCheck.set(true);
        }
    }

    @Override
    public void onDrinkTypeCheck() {
        if (isDrinkTypeCheck.get()) {
            isDrinkTypeCheck.set(false);
        } else {
            isDrinkTypeCheck.set(true);
        }
    }

    @Override
    public void onStayTypeCheck() {
        if (isStayTypeCheck.get()) {
            isStayTypeCheck.set(false);
        } else {
            isStayTypeCheck.set(true);
        }
    }

    @Override
    public void loadUserSetting() {
        SharedPreferences prefs = TravelSharedPreference.getINSTANCE();

        boolean isSeeCheck = prefs.getBoolean(Constant.SHARED_PLACE_SEE_TYPE, false);
        boolean isFoodiesCheck = prefs.getBoolean(Constant.SHARED_PLACE_FOODIES_TYPE, false);
        boolean isDrinkCheck = prefs.getBoolean(Constant.SHARED_PLACE_DRINK_TYPE, false);
        boolean isStayCheck = prefs.getBoolean(Constant.SHARED_PLACE_STAY_TYPE, false);

        if (!isSeeCheck && !isFoodiesCheck && !isDrinkCheck && !isStayCheck) {
            //do nothing, default load see place

        } else {
            isSeeTypeCheck.set(isSeeCheck);
            isFoodiesTypeCheck.set(isFoodiesCheck);
            isDrinkTypeCheck.set(isDrinkCheck);
            isStayTypeCheck.set(isStayCheck);
        }

    }
}
