package com.example.dattienbkhn.travel.screen.friendlist;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.example.dattienbkhn.travel.BR;
import com.example.dattienbkhn.travel.databinding.ItemFriendSearchBinding;
import com.example.dattienbkhn.travel.model.googleinfor.GoogleInfor;
import com.example.dattienbkhn.travel.network.message.ResponseCode;
import com.example.dattienbkhn.travel.network.message.WrapperResponse;
import com.example.dattienbkhn.travel.network.message.account.AccResponse;
import com.example.dattienbkhn.travel.network.message.user.UserSearch;
import com.example.dattienbkhn.travel.repository.AppRepository;
import com.example.dattienbkhn.travel.screen.main.mefragment.ItemFriendViewModel;
import com.squareup.picasso.Picasso;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by tiendatbkhn on 25/04/2018.
 */

public class ItemFriendsSearchViewModel extends BaseObservable {
    private UserSearch userSearch;
    private ItemFriendViewModel.IItemFriendClick itemFriendClick;
    private ItemFriendSearchBinding binding;

    public ItemFriendsSearchViewModel(UserSearch userSearch, ItemFriendViewModel.IItemFriendClick itemFriendClick, ItemFriendSearchBinding binding) {
        this.userSearch = userSearch;
        this.itemFriendClick = itemFriendClick;
        this.binding = binding;
        getAvatar(userSearch.getAccId());
    }

    private void getAvatar(int accId) {
        if (userSearch.getAvatar() != null) {
            byte[] imrArr = Base64.decode(userSearch.getAvatar(), Base64.DEFAULT);
            Bitmap bm = BitmapFactory.decodeByteArray(imrArr, 0, imrArr.length);
            binding.imgUserAvatar.setImageBitmap(bm);
        } else {
            getUserImage(accId);
        }
    }

    private void getUserImage(int accId) {
        AppRepository.getAccountRepo().getAccount(accId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WrapperResponse<AccResponse>>() {
                    @Override
                    public void accept(WrapperResponse<AccResponse> accResponseWrapperResponse) throws Exception {
                        if (accResponseWrapperResponse.getMessage()
                                .equalsIgnoreCase(ResponseCode.Account.toString())) {

                            if (userSearch.getIsFaceBook() == 1) {
                                String userAvatar = "https://graph.facebook.com/"
                                        + accResponseWrapperResponse.getData().getUsername()
                                        + "/picture?type=large";
                                Picasso.with(binding.imgUserAvatar.getContext()).load(userAvatar).into(binding.imgUserAvatar);
                            } else if (userSearch.getIsGoogle() == 1) {
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

    public void onFriendItemClick() {
        itemFriendClick.onFriendItemClick(userSearch.getAccId());
    }
    @Bindable
    public UserSearch getUserSearch() {
        return userSearch;
    }

    public void setUserSearch(UserSearch userSearch) {
        this.userSearch = userSearch;
        notifyPropertyChanged(BR.userSearch);
    }
}
