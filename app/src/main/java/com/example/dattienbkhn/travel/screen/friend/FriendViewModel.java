package com.example.dattienbkhn.travel.screen.friend;

import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.example.dattienbkhn.travel.database.sharedPreference.TravelSharedPreference;
import com.example.dattienbkhn.travel.databinding.ActivityFriendBinding;
import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.model.app.User;
import com.example.dattienbkhn.travel.model.googleinfor.GoogleInfor;
import com.example.dattienbkhn.travel.network.message.ResponseCode;
import com.example.dattienbkhn.travel.network.message.WrapperResponse;
import com.example.dattienbkhn.travel.network.message.account.AccResponse;
import com.example.dattienbkhn.travel.repository.AppRepository;
import com.example.dattienbkhn.travel.screen.BaseViewNavigator;
import com.example.dattienbkhn.travel.screen.lovedplace.LovedPlaceAdapter;
import com.example.dattienbkhn.travel.utils.Constant;
import com.example.dattienbkhn.travel.utils.dialog.DialogManager;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by tiendatbkhn on 02/04/2018.
 */

public class FriendViewModel extends BaseObservable implements FriendContract.ViewModel {
    private ActivityFriendBinding binding;
    private DialogManager mDialogManager;
    private FriendContract.View mNavigator;
    private int mCurFriendId;
    private boolean isLogin;
    private User mCurUser;
    private LovedPlaceAdapter lovedPlaceAdapter;
    private boolean recentlyFollowState;

    public ObservableField<Boolean> isFollow = new ObservableField<>();
    public ObservableField<Boolean> isStream = new ObservableField<>();
    public ObservableField<Boolean> isHasLovePlace = new ObservableField<>();
    public ObservableField<String> mAddress = new ObservableField<>();
    public ObservableField<String> mBirthday = new ObservableField<>();
    public ObservableField<String> mGender = new ObservableField<>();
    public ObservableField<String> mName = new ObservableField<>();


    public FriendViewModel(DialogManager mDialogManager) {
        this.mDialogManager = mDialogManager;
    }

    @Override
    public void onStart() {
        getFriendInfor(mCurFriendId);

        SharedPreferences prefs = TravelSharedPreference.getINSTANCE();
        isLogin = prefs.getBoolean(Constant.SHARED_LOGIN_STATE, false);
        if (isLogin) {
            checkFollow(prefs.getInt(Constant.SHARED_LOGIN_ACC_ID, -1), mCurFriendId);
        }

        getStreamState(mCurFriendId);
        getLovedPlace(mCurFriendId);

    }

    @Override
    public void setViewNavigator(BaseViewNavigator viewNavigator) {
        mNavigator = (FriendContract.View) viewNavigator;
    }

    @Override
    public void getFriendInfor(final int friendId) {
        AppRepository.getAccountRepo().getUserInfor(friendId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mDialogManager.showProgressDialog();
                    }
                })
                .subscribe(new Consumer<WrapperResponse<User>>() {
                    @Override
                    public void accept(WrapperResponse<User> userWrapperResponse) throws Exception {
                        if (userWrapperResponse.getMessage().equalsIgnoreCase(ResponseCode.UserInfor.toString())) {
                            User userRes = userWrapperResponse.getData();
                            mAddress.set(userRes.getAddressString());
                            mGender.set(userRes.getGenderString());
                            mBirthday.set(userRes.getBirthDayString());
                            mName.set(userRes.getName());
                            mCurUser = userWrapperResponse.getData();

                            if (userRes.getAvatar() != null) {
                                byte[] imrArr = Base64.decode(userRes.getAvatar(), Base64.DEFAULT);
                                Bitmap bm = BitmapFactory.decodeByteArray(imrArr, 0, imrArr.length);
                                binding.imgAvatar.setImageBitmap(bm);
                            }

                            if (userRes.getCoverImage() != null) {
                                byte[] imrArr = Base64.decode(userRes.getCoverImage(), Base64.DEFAULT);
                                Bitmap bm = BitmapFactory.decodeByteArray(imrArr, 0, imrArr.length);
                                binding.imgCover.setImageBitmap(bm);
                            }

                            if (userRes.getAvatar() == null || userRes.getCoverImage() == null) {
                                setUserImage(friendId);
                            }
                        }
                        mDialogManager.dismissProgressDialog();
                    }
                });
    }

    @Override
    public void getLovedPlace(int friendId) {
        AppRepository.getPlaceRepo().getLovedPlaceByUser(friendId)
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
                            isHasLovePlace.set(Boolean.TRUE);
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

    private void setUserImage(final int accId) {
        Log.e("tuan"," : chay setUserImage");
        AppRepository.getAccountRepo().getAccount(accId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WrapperResponse<AccResponse>>() {
                    @Override
                    public void accept(WrapperResponse<AccResponse> accResponseWrapperResponse) throws Exception {
                        if (accResponseWrapperResponse.getMessage()
                                .equalsIgnoreCase(ResponseCode.Account.toString())) {

                            if (accResponseWrapperResponse.getData().getIsFaceBook() == 1) {
                                loadFaceBookInfor(accResponseWrapperResponse.getData().getUsername());
                            } else if (accResponseWrapperResponse.getData().getIsGoogle() == 1) {
                                loadGoogleInfor(accResponseWrapperResponse.getData().getUsername());
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("item_cmt", throwable.getMessage());
                    }
                });
    }

    public void loadFaceBookInfor(String fbId) {
        SharedPreferences prefs = TravelSharedPreference.getINSTANCE();

        if (mCurUser.getAvatar() == null) {
            String userAvatar = "https://graph.facebook.com/"
                    + fbId
                    + "/picture?type=large";
            Picasso.with(binding.imgAvatar.getContext()).load(userAvatar).into(binding.imgAvatar);
        }

    }

    public void loadGoogleInfor(String ggId) {
        if (mCurUser.getAvatar() == null) {
            AppRepository.getAccountRepo().getGoogleInfor(ggId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<GoogleInfor>() {
                        @Override
                        public void accept(GoogleInfor googleInfor) throws Exception {
                            if (googleInfor.getEntry().getGphotothumbnail().gett() != null) {
                                Picasso.with(binding.imgAvatar.getContext()).
                                        load(googleInfor.getEntry().getGphotothumbnail().gett())
                                        .into(binding.imgAvatar);
                            }
                        }
                    });
        }
    }

    @Override
    public void checkFollow(int accId, int friendId) {
        AppRepository.getAccountRepo().checkFollow(accId, friendId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WrapperResponse<Boolean>>() {
                    @Override
                    public void accept(WrapperResponse<Boolean> booleanWrapperResponse) throws Exception {
                        if (booleanWrapperResponse.getMessage().equalsIgnoreCase(ResponseCode.FriendCheck.toString())) {
                            isFollow.set(booleanWrapperResponse.getData());
                            recentlyFollowState = booleanWrapperResponse.getData();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("friend_infor", " : " + throwable.getMessage());
                    }
                });
    }

    @Override
    public void getStreamState(int friendId) {
        AppRepository.getAccountRepo().getStreamState(friendId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WrapperResponse<Boolean>>() {
                    @Override
                    public void accept(WrapperResponse<Boolean> booleanWrapperResponse) throws Exception {
                        if (booleanWrapperResponse.getMessage().equalsIgnoreCase(ResponseCode.StreamState.toString())) {
                            isStream.set(booleanWrapperResponse.getData());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("friend_infor", " : " + throwable.getMessage());
                    }
                });
    }

    @Override
    public void follow(int accId, int friendId, boolean isFollow) {
        AppRepository.getAccountRepo().followFriend(accId, friendId, isFollow)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WrapperResponse<Boolean>>() {
                    @Override
                    public void accept(WrapperResponse<Boolean> booleanWrapperResponse) throws Exception {
                        if (booleanWrapperResponse.getMessage().equalsIgnoreCase(ResponseCode.Friend.toString())) {
                            Log.e("follow", " : success");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("follow", " : " + throwable.getMessage());
                    }
                });
    }

    @Override
    public void playLiveStream(int friendId) {
        mNavigator.goToPlayStreamAct(friendId);
    }

    @Override
    public void onFollowClick() {
        SharedPreferences prefs = TravelSharedPreference.getINSTANCE();
        if (prefs.getBoolean(Constant.SHARED_LOGIN_STATE, false)) {
            if (isFollow.get()) {
                isFollow.set(Boolean.FALSE);
            } else {
                isFollow.set(Boolean.TRUE);
            }
        } else {
            mNavigator.onErrorMessage("Login to continues");
        }
    }

    @Override
    public void onStreamPlayClick() {
        if (isStream.get()) {
            playLiveStream(mCurFriendId);
        } else {
            mNavigator.onErrorMessage("Not Stream");
        }

    }

    @Override
    public void onVodClick() {
        mNavigator.goToVodAct(mCurFriendId,mName.get());
    }

    @Override
    public void onStop() {
        SharedPreferences prefs = TravelSharedPreference.getINSTANCE();
        if (prefs.getBoolean(Constant.SHARED_LOGIN_STATE, false)) {
            if (recentlyFollowState != isFollow.get()) {
                follow(prefs.getInt(Constant.SHARED_LOGIN_ACC_ID, -1), mCurFriendId, isFollow.get());
            }
        }
    }

    @Override
    public void updateStreamState(boolean streamState) {
        isStream.set(streamState);
    }

    @Override
    public void onBackClick() {
        mNavigator.goFinish();
    }

    public int getCurFriendId() {
        return mCurFriendId;
    }

    public void setCurFriendId(int mCurFriendId) {
        this.mCurFriendId = mCurFriendId;
    }

    public ActivityFriendBinding getBinding() {
        return binding;
    }

    public void setBinding(ActivityFriendBinding binding) {
        this.binding = binding;
    }

    @Bindable
    public LovedPlaceAdapter getLovedPlaceAdapter() {
        return lovedPlaceAdapter;
    }

    public void setLovedPlaceAdapter(LovedPlaceAdapter lovedPlaceAdapter) {
        this.lovedPlaceAdapter = lovedPlaceAdapter;
    }
}
