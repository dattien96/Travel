package com.example.dattienbkhn.travel.repository.remote;

import com.example.dattienbkhn.travel.model.app.Comment;
import com.example.dattienbkhn.travel.model.app.Friend;
import com.example.dattienbkhn.travel.model.app.Love;
import com.example.dattienbkhn.travel.model.app.Rate;
import com.example.dattienbkhn.travel.model.app.User;
import com.example.dattienbkhn.travel.model.googleinfor.GoogleInfor;
import com.example.dattienbkhn.travel.network.api.GoogleInforApi;
import com.example.dattienbkhn.travel.network.api.TravelApi;
import com.example.dattienbkhn.travel.network.message.WrapperResponse;
import com.example.dattienbkhn.travel.network.message.account.AccRequest;
import com.example.dattienbkhn.travel.network.message.account.AccResponse;
import com.example.dattienbkhn.travel.network.message.user.UserSearch;
import com.example.dattienbkhn.travel.network.service.GoogleInforService;
import com.example.dattienbkhn.travel.network.service.TravelService;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by tiendatbkhn on 16/03/2018.
 */

public class AccountRemoteRepo implements IAccountRemoteDataSource {

    private static TravelApi mApi;
    private static GoogleInforApi mGgInforApi;

    public AccountRemoteRepo() {
        mApi = TravelService.getTravelApiInstance();
        mGgInforApi = GoogleInforService.getGoogleInforApiInstance();
    }

    @Override
    public Observable<WrapperResponse<Integer>> checkExistAcc(String userName, String accType) {
        return mApi.checkExistAcc(userName,accType);
    }

    @Override
    public Observable<WrapperResponse<User>> getUserInfor(int accId) {
        return mApi.getUserInfor(accId);
    }

    @Override
    public Observable<WrapperResponse<AccResponse>> getAccount(int accId) {
        return mApi.getAccount(accId);
    }

    @Override
    public Observable<WrapperResponse<Integer>> createAccount(AccRequest accRequest, String userName) {
        return mApi.createAccount(accRequest,userName);
    }

    @Override
    public Observable<WrapperResponse<Boolean>> changeUserInfor(User userRequest) {
        return mApi.changeUserInfor(userRequest);
    }

    @Override
    public Observable<WrapperResponse<Boolean>> checkLove(int accId, int placeId) {
        return mApi.checkLove(accId, placeId);
    }

    @Override
    public Observable<WrapperResponse<Rate>> getUserRateOfplace(int accId, int placeId) {
        return mApi.getUserRateOfplace(accId,placeId);
    }

    @Override
    public Observable<WrapperResponse<Boolean>> saveLove(Love love, boolean isLove) {
        return mApi.saveLove(love, isLove);
    }

    @Override
    public Observable<WrapperResponse<Boolean>> saveRate(Rate rate) {
        return mApi.saveRate(rate);
    }

    @Override
    public Observable<WrapperResponse<Boolean>> postComment(Comment cmt) {
        return mApi.postComment(cmt);
    }

    @Override
    public Observable<GoogleInfor> getGoogleInfor(String userId) {
        return mGgInforApi.getGoogleInfor(userId);
    }

    @Override
    public Observable<User> postbyte(User userRequest) {
        return mApi.postbyte(userRequest);
    }

    @Override
    public Observable<WrapperResponse<List<Friend>>> getFriends(int accId) {
        return mApi.getFriends(accId);
    }

    @Override
    public Observable<WrapperResponse<List<Friend>>> getTopFriends(int accId) {
        return mApi.getTopFriends(accId);
    }

    @Override
    public Observable<WrapperResponse<List<UserSearch>>> findFriendByName(int accId, String searchname) {
        return mApi.findFriendByName(accId, searchname);
    }

    @Override
    public Observable<WrapperResponse<Boolean>> getStreamState(int accId) {
        return mApi.getStreamState(accId);
    }

    @Override
    public Observable<WrapperResponse<Boolean>> saveStreamState(int accId, int streamState) {
        return mApi.saveStreamState(accId, streamState);
    }

    @Override
    public Observable<WrapperResponse<Boolean>> followFriend(int accId, int friendId,boolean isFollow) {
        return mApi.followFriend(accId, friendId,isFollow);
    }

    @Override
    public Observable<WrapperResponse<Boolean>> checkFollow(int accId, int friendId) {
        return mApi.checkFollow(accId, friendId);
    }

    @Override
    public Observable<WrapperResponse<List<String>>> getVOD(int accId) {
        return mApi.getVOD(accId);
    }

    @Override
    public Observable<WrapperResponse<String>> getThumbnail(String videoName) {
        return mApi.getThumbnail(videoName);
    }

    @Override
    public Observable<WrapperResponse<Boolean>> makeThumbnail(String videoName) {
        return mApi.makeThumbnail(videoName);
    }
}
