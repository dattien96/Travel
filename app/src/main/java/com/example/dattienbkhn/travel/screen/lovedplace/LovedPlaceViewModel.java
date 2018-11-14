package com.example.dattienbkhn.travel.screen.lovedplace;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;

import com.example.dattienbkhn.travel.databinding.ActivityLovedPlaceBinding;
import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.network.message.ResponseCode;
import com.example.dattienbkhn.travel.network.message.WrapperResponse;
import com.example.dattienbkhn.travel.repository.AppRepository;
import com.example.dattienbkhn.travel.screen.BaseViewNavigator;
import com.example.dattienbkhn.travel.utils.dialog.DialogManager;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by tiendatbkhn on 05/04/2018.
 */

public class LovedPlaceViewModel extends BaseObservable implements LovedPlaceContract.ViewModel {
    private ActivityLovedPlaceBinding binding;
    private DialogManager mDialogManager;
    private LovedPlaceContract.View mNavigator;
    private LovedPlaceAdapter lovedPlaceAdapter;
    private int mCurrentAccId;

    public LovedPlaceViewModel(DialogManager mDialogManager) {
        this.mDialogManager = mDialogManager;
    }

    @Override
    public void onStart() {

        getLovedPlace(mCurrentAccId);
    }

    @Override
    public void setViewNavigator(BaseViewNavigator viewNavigator) {
        mNavigator = (LovedPlaceContract.View) viewNavigator;
    }

    @Override
    public void getLovedPlace(int accId) {
        AppRepository.getPlaceRepo().getLovedPlaceByUser(accId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mDialogManager.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<WrapperResponse<List<Place>>>() {
                    @Override
                    public void accept(WrapperResponse<List<Place>> listWrapperResponse) throws Exception {
                        if (listWrapperResponse.getMessage().equalsIgnoreCase(ResponseCode.LovedPlace.toString())) {
                            lovedPlaceAdapter.setData(listWrapperResponse.getData());
                        }
                        mDialogManager.dismissProgressDialog();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("friend_loved_place", " : " + throwable.getMessage());
                        mDialogManager.dismissProgressDialog();
                    }
                });
    }

    @Override
    public void onBackClick() {
        mNavigator.goFinish();
    }

    public void setBinding(ActivityLovedPlaceBinding binding) {
        this.binding = binding;
    }

    @Bindable
    public LovedPlaceAdapter getLovedPlaceAdapter() {
        return lovedPlaceAdapter;
    }

    public void setLovedPlaceAdapter(LovedPlaceAdapter lovedPlaceAdapter) {
        this.lovedPlaceAdapter = lovedPlaceAdapter;
    }

    public int getmCurrentAccId() {
        return mCurrentAccId;
    }

    public void setmCurrentAccId(int mCurrentAccId) {
        this.mCurrentAccId = mCurrentAccId;
    }
}
