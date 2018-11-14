package com.example.dattienbkhn.travel.screen.vod;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;

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
 * Created by tiendatbkhn on 29/04/2018.
 */

public class VodViewModel extends BaseObservable implements VodContract.ViewModel {
    private VodContract.View mNavigator;
    private DialogManager mDialogManager;
    private int mCurAccId;
    private VodAdapter vodAdapter;

    public final ObservableField<String> mCurName = new ObservableField<>();
    public final ObservableField<Boolean> isHasData = new ObservableField<>();

    public VodViewModel(DialogManager mDialogManager) {
        this.mDialogManager = mDialogManager;
    }

    @Override
    public void onStart() {
        loadVod(mCurAccId);
    }

    @Override
    public void setViewNavigator(BaseViewNavigator viewNavigator) {
        mNavigator = (VodContract.View) viewNavigator;
    }

    @Override
    public void loadVod(int accId) {
        AppRepository.getAccountRepo().getVOD(accId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mDialogManager.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<WrapperResponse<List<String>>>() {
                    @Override
                    public void accept(WrapperResponse<List<String>> listWrapperResponse) throws Exception {
                        if (listWrapperResponse.getMessage().equalsIgnoreCase(ResponseCode.VODStream.toString())) {
                            vodAdapter.setData(listWrapperResponse.getData());
                            isHasData.set(Boolean.TRUE);
                        }
                        mDialogManager.dismissProgressDialog();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mNavigator.onErrorMessage("Error when load Vod!");
                        mDialogManager.dismissProgressDialog();
                    }
                });
    }

    @Override
    public void onBackClick() {
        mNavigator.finishView();
    }

    public int getCurAccId() {
        return mCurAccId;
    }

    public void setCurAccId(int mCurAccId) {
        this.mCurAccId = mCurAccId;
    }

    public VodAdapter getVodAdapter() {
        return vodAdapter;
    }

    public void setVodAdapter(VodAdapter vodAdapter) {
        this.vodAdapter = vodAdapter;
    }

    public void setCurName (String s) {
        mCurName.set(s);
    }
}
