package com.example.dattienbkhn.travel.screen.detailsplace;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.example.dattienbkhn.travel.BR;
import com.example.dattienbkhn.travel.databinding.ItemCommentBinding;
import com.example.dattienbkhn.travel.model.app.Comment;
import com.example.dattienbkhn.travel.model.app.User;
import com.example.dattienbkhn.travel.model.googleinfor.GoogleInfor;
import com.example.dattienbkhn.travel.network.message.ResponseCode;
import com.example.dattienbkhn.travel.network.message.WrapperResponse;
import com.example.dattienbkhn.travel.network.message.account.AccResponse;
import com.example.dattienbkhn.travel.repository.AppRepository;
import com.squareup.picasso.Picasso;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by tiendatbkhn on 20/03/2018.
 */

public class ItemCommentViewModel extends BaseObservable {
    private Comment comment;
    private ItemCommentBinding binding;
    private IItemUserClick itemUserClick;
    public final ObservableField<User> user = new ObservableField<>();
    public final ObservableField<String> userAvatar = new ObservableField<>();


    public ItemCommentViewModel(Comment comment, ItemCommentBinding binding,IItemUserClick itemUserClick) {
        this.comment = comment;
        this.binding = binding;
        this.itemUserClick = itemUserClick;
    }

    public void onStart() {
        getUserInfor(comment.getAccId());
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

                                            }
                                        });
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

    public void onUserItemClick() {
        itemUserClick.onUserItemClick(comment.getAccId());
    }

    @Bindable
    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
        notifyPropertyChanged(BR.comment);
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
                                setUserImage(comment.getAccId());
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

    public interface IItemUserClick {
        void onUserItemClick(int accId);
    }
}
