package com.example.dattienbkhn.travel.screen.comment;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.util.Log;

import com.example.dattienbkhn.travel.databinding.ActivityCommentBinding;
import com.example.dattienbkhn.travel.model.app.Comment;
import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.network.message.ResponseCode;
import com.example.dattienbkhn.travel.network.message.WrapperResponse;
import com.example.dattienbkhn.travel.repository.AppRepository;
import com.example.dattienbkhn.travel.screen.BaseViewNavigator;
import com.example.dattienbkhn.travel.screen.detailsplace.CommentAdapter;
import com.example.dattienbkhn.travel.utils.dialog.DialogManager;

import java.text.SimpleDateFormat;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by tiendatbkhn on 20/03/2018.
 */

public class CommentViewModel extends BaseObservable implements CommentContract.ViewModel {
    private ActivityCommentBinding mActivityCommentBinding;
    private CommentContract.View mNavigator;
    private DialogManager mDialogManager;
    private CommentAdapter mCommentAdapter;
    private int accId;
    private Place place;


    public final ObservableField<Boolean> isSendReview = new ObservableField<>();

    public CommentViewModel(DialogManager mDialogManager) {
        this.mDialogManager = mDialogManager;
    }

    @Override
    public void onStart() {
        isSendReview.set(Boolean.FALSE);
        loadCommentOfPlace(place.getPlaceId());
    }

    @Override
    public void setViewNavigator(BaseViewNavigator viewNavigator) {
        mNavigator = (CommentContract.View) viewNavigator;
    }

    @Override
    public void sendComment(Comment cmt) {
        AppRepository.getAccountRepo().postComment(cmt).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WrapperResponse<Boolean>>() {
                    @Override
                    public void accept(WrapperResponse<Boolean> booleanWrapperResponse) throws Exception {
                        if (booleanWrapperResponse.getMessage().equalsIgnoreCase(ResponseCode.Comments.toString())
                                && booleanWrapperResponse.getData()) {
                            mNavigator.onErrorMessage("Send comment success!");
                            isSendReview.set(Boolean.FALSE);
                            loadCommentOfPlace(place.getPlaceId());
                        }
                    }
                });
    }

    @Override
    public void loadCommentOfPlace(int idPlace) {
        AppRepository.getPlaceRepo().getComments(idPlace)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WrapperResponse<List<Comment>>>() {
                    @Override
                    public void accept(WrapperResponse<List<Comment>> listWrapperResponse) throws Exception {
                        if (listWrapperResponse.getMessage().equalsIgnoreCase(ResponseCode.Comments.toString())) {
                            mCommentAdapter.setData(listWrapperResponse.getData());
                            mActivityCommentBinding.rcReview.smoothScrollToPosition(listWrapperResponse.getData().size() - 1);


                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("get_cmt", " error : " + throwable.getMessage());
                    }
                });
    }

    @Override
    public void onbackClick() {
        mNavigator.goFinish();
    }

    @Override
    public void onWriteCmtClick() {
        if (accId == -1) {
            mNavigator.onErrorMessage("Login to continue!");
        } else {
            isSendReview.set(Boolean.TRUE);
        }
    }

    @Override
    public void onSendReviewClick() {
        if (mActivityCommentBinding.edReview.getText().length() >= 1000) {
            mNavigator.onErrorMessage("Review content is too long!");
        } else if (mActivityCommentBinding.edReview.getText().length() == 0) {
            mNavigator.onErrorMessage("Review content is empty!");
        } else {

            java.util.Date date = new java.util.Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Comment comment = new Comment();
            comment.setAccId(accId);
            comment.setCmtContent(mActivityCommentBinding.edReview.getText().toString());
            comment.setCmtTime(sdf.format(date));
            comment.setPlaceId(place.getPlaceId());

            sendComment(comment);
        }


    }

    @Override
    public void onCancelSendClick() {
        isSendReview.set(Boolean.FALSE);
    }

    @Bindable
    public CommentAdapter getCommentAdapter() {
        return mCommentAdapter;
    }

    public void setCommentAdapter(CommentAdapter mCommentAdapter) {
        this.mCommentAdapter = mCommentAdapter;
    }

    public int getAccId() {
        return accId;
    }

    public void setAccId(int accId) {
        this.accId = accId;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public void setActivityCommentBinding(ActivityCommentBinding binding) {
        this.mActivityCommentBinding = binding;
    }
}
