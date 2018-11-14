package com.example.dattienbkhn.travel.screen.main.mefragment;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.example.dattienbkhn.travel.BR;
import com.example.dattienbkhn.travel.databinding.ItemFriendBinding;
import com.example.dattienbkhn.travel.model.app.Friend;
import com.example.dattienbkhn.travel.model.app.User;
import com.example.dattienbkhn.travel.model.googleinfor.GoogleInfor;
import com.example.dattienbkhn.travel.network.message.ResponseCode;
import com.example.dattienbkhn.travel.network.message.WrapperResponse;
import com.example.dattienbkhn.travel.network.message.account.AccResponse;
import com.example.dattienbkhn.travel.repository.AppRepository;
import com.example.dattienbkhn.travel.screen.stream.SocketConfig;
import com.github.nkzawa.emitter.Emitter;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by tiendatbkhn on 02/04/2018.
 */

public class ItemFriendViewModel extends BaseObservable {
    private Friend friend;
    private ItemFriendBinding binding;
    private IItemFriendClick itemFriendClick;
    public final ObservableField<User> user = new ObservableField<>();
    public final ObservableField<Boolean> isStream = new ObservableField<>();
    private boolean bStreamStateTmp;

    public ItemFriendViewModel(Friend friend, ItemFriendBinding binding, IItemFriendClick itemFriendClick) {
        this.friend = friend;
        this.binding = binding;
        this.itemFriendClick = itemFriendClick;
        if (SocketConfig.getSocketInstance() != null) {
            SocketConfig.getSocketInstance().on("new_stream", onNewMessage);
        }
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {

        @Override
        public void call(final Object... args) {

            Completable.fromAction(new Action() {
                @Override
                public void run() throws Exception {
                    int accId;
                    int streamState;
                    JSONObject data = (JSONObject) args[0];
                    try {
                        accId = data.getInt("accId");
                        if (friend.getFriendId() == accId) {
                            try {
                                streamState = data.getInt("streamState");
                                bStreamStateTmp = (streamState == 1) ? true : false;

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    } catch (JSONException e) {
                        return;
                    }
                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onComplete() {
                            isStream.set(bStreamStateTmp);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }
                    });
        }
    };

    public void onFriendItemClick() {
        itemFriendClick.onFriendItemClick(friend.getFriendId());
    }

    public void onStart() {
        getUserInfor(friend.getFriendId());
        getStreamState(friend.getFriendId());
    }

    public void getStreamState(int accId) {
        AppRepository.getAccountRepo().getStreamState(accId)
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
                        Log.e("stream_state", " : " + throwable.getMessage());
                    }
                });
    }

    public void getUserInfor(int accId) {
        AppRepository.getAccountRepo().getUserInfor(accId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WrapperResponse<User>>() {
                    @Override
                    public void accept(WrapperResponse<User> userWrapperResponse) throws Exception {
                        if (userWrapperResponse.getMessage().equalsIgnoreCase(ResponseCode.UserInfor.toString())) {
                            user.set(userWrapperResponse.getData());

                            if (user.get().getAvatar() != null) {
                                byte[] imrArr = Base64.decode(user.get().getAvatar(), Base64.DEFAULT);
                                Bitmap bm = BitmapFactory.decodeByteArray(imrArr, 0, imrArr.length);
                                binding.imgUserAvatar.setImageBitmap(bm);
                            } else {
                                setUserImage(friend.getFriendId());
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("item_friend : ", throwable.getMessage());
                    }
                });
    }

    private void setUserImage(int accId) {
        AppRepository.getAccountRepo().getAccount(accId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WrapperResponse<AccResponse>>() {
                    @Override
                    public void accept(WrapperResponse<AccResponse> accResponseWrapperResponse) throws Exception {
                        if (accResponseWrapperResponse.getMessage()
                                .equalsIgnoreCase(ResponseCode.Account.toString())) {

                            if (accResponseWrapperResponse.getData().getIsFaceBook() == 1) {
                                String userAvatar = "https://graph.facebook.com/"
                                        + accResponseWrapperResponse.getData().getUsername()
                                        + "/picture?type=large";
                                Picasso.with(binding.imgUserAvatar.getContext()).load(userAvatar).into(binding.imgUserAvatar);
                            } else if (accResponseWrapperResponse.getData().getIsGoogle() == 1) {
                                AppRepository.getAccountRepo().getGoogleInfor(accResponseWrapperResponse.getData().getUsername())
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Consumer<GoogleInfor>() {
                                            @Override
                                            public void accept(GoogleInfor googleInfor) throws Exception {
                                                if (googleInfor.getEntry().getGphotothumbnail().gett() != null) {
                                                    Picasso.with(binding.imgUserAvatar.getContext()).
                                                            load(googleInfor.getEntry().getGphotothumbnail().gett())
                                                            .into(binding.imgUserAvatar);
                                                }
                                            }
                                        }, new Consumer<Throwable>() {
                                            @Override
                                            public void accept(Throwable throwable) throws Exception {
                                                Log.e("item_friend : ", throwable.getMessage());
                                            }
                                        });
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("item_friend : ", throwable.getMessage());
                    }
                });
    }

    @Bindable
    public Friend getFriend() {
        return friend;
    }

    public void setFriend(Friend friend) {
        this.friend = friend;
        notifyPropertyChanged(BR.friend);
    }

    public interface IItemFriendClick {
        void onFriendItemClick(int friendId);
    }
}
