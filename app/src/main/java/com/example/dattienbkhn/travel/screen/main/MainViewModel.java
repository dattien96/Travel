package com.example.dattienbkhn.travel.screen.main;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;

import com.example.dattienbkhn.travel.model.app.User;
import com.example.dattienbkhn.travel.network.message.WrapperResponse;
import com.example.dattienbkhn.travel.repository.AppRepository;
import com.example.dattienbkhn.travel.screen.BaseViewNavigator;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by dattienbkhn on 08/02/2018.
 */

public class MainViewModel extends BaseObservable implements MainContract.ViewModel {

    private MainContract.View mActNavigator;
    public final ObservableField<Boolean> isVisibleHomeTabIcon = new ObservableField<>();
    public final ObservableField<Boolean> isVisibleMeTabIcon = new ObservableField<>();
    public final ObservableField<Boolean> isVisibleFavoriteTabIcon = new ObservableField<>();

    @Override
    public void onStart() {
        //do not anything because no data need to be load
        isVisibleHomeTabIcon.set(true);
        isVisibleMeTabIcon.set(false);
        isVisibleFavoriteTabIcon.set(false);
    }

    @Override
    public void setViewNavigator(BaseViewNavigator viewNavigator) {
        mActNavigator = (MainContract.View) viewNavigator;
    }


    @Override
    public void onHomeFrgClick() {
        if (!isVisibleHomeTabIcon.get()) {
            mActNavigator.showHomeFrg();
            isVisibleHomeTabIcon.set(true);
            isVisibleMeTabIcon.set(false);
            isVisibleFavoriteTabIcon.set(false);
        }

    }

    @Override
    public void onMeFrgClick() {
        if (!isVisibleMeTabIcon.get()) {
            mActNavigator.showMeFrg();
            isVisibleHomeTabIcon.set(false);
            isVisibleFavoriteTabIcon.set(false);
            isVisibleMeTabIcon.set(true);
        }
    }

    @Override
    public void onFavoriteFrgClick() {
        if (!isVisibleFavoriteTabIcon.get()) {
            mActNavigator.showFavoriteFrg();
            isVisibleHomeTabIcon.set(false);
            isVisibleMeTabIcon.set(false);
            isVisibleFavoriteTabIcon.set(true);
        }
    }

    @Override
    public void notifyNewStream(int accId,int streamState) {
        if (streamState == 1) {
            AppRepository.getAccountRepo().getUserInfor(accId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<WrapperResponse<User>>() {
                        @Override
                        public void accept(WrapperResponse<User> userWrapperResponse) throws Exception {

                            mActNavigator.showNotificationStream(userWrapperResponse.getData().getAccId(),
                                    userWrapperResponse.getData().getName());
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            //error
                        }
                    });
        }
    }


}
