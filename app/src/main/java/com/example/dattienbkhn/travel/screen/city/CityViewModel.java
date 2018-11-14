package com.example.dattienbkhn.travel.screen.city;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.util.Log;

import com.example.dattienbkhn.travel.databinding.ActivityCityBinding;
import com.example.dattienbkhn.travel.model.app.City;
import com.example.dattienbkhn.travel.network.message.WrapperResponse;
import com.example.dattienbkhn.travel.repository.AppRepository;
import com.example.dattienbkhn.travel.screen.BaseViewNavigator;
import com.example.dattienbkhn.travel.utils.dialog.DialogManager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dattienbkhn on 09/02/2018.
 */

public class CityViewModel extends BaseObservable implements CityContract.ViewModel {
    private CityContract.View mNavigator;
    private DialogManager mDialogManager;
    private CityViewPagerAdapter mAdapter;
    private City mCurrenCity;
    private ActivityCityBinding binding;

    public final ObservableField<Integer> currentTab = new ObservableField<>();

    public CityViewModel(DialogManager mDialogManager) {
        this.mDialogManager = mDialogManager;
    }

    @Override
    public void onStart() {
        //begin tab is home
        currentTab.set(0);

        increaseCityViews(mCurrenCity.getCityId());
    }


    @Override
    public void setViewNavigator(BaseViewNavigator viewNavigator) {
        this.mNavigator = (CityContract.View) viewNavigator;
    }

    @Bindable
    public CityViewPagerAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(CityViewPagerAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    @Bindable
    public City getCurrenCity() {
        return mCurrenCity;
    }

    public void setCurrenCity(City mCurrenCity) {
        this.mCurrenCity = mCurrenCity;
    }

    @Override
    public void onBackClick() {
        mNavigator.goBack();
    }

    @Override
    public void onSearchClick() {
        mNavigator.goSearchAct();
    }

    @Override
    public void increaseCityViews(int cityId) {
        AppRepository.getCityRepo().increaseCityViews(cityId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WrapperResponse<Integer>>() {
                    @Override
                    public void accept(WrapperResponse<Integer> integerWrapperResponse) throws Exception {
                        Log.e("city_views", " : " + integerWrapperResponse.getData());
                    }
                });
    }

    /**
     * Call By Frg
     *
     * @param pos
     */

    public void setTabViewPager(int pos) {
        currentTab.set(pos);
        binding.vpCity.setCurrentItem(pos,true);
    }

    public void setBinding(ActivityCityBinding binding) {
        this.binding = binding;
    }
}
