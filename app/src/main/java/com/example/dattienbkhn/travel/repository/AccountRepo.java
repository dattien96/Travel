package com.example.dattienbkhn.travel.repository;

import com.example.dattienbkhn.travel.model.app.Comment;
import com.example.dattienbkhn.travel.model.app.Friend;
import com.example.dattienbkhn.travel.model.app.Love;
import com.example.dattienbkhn.travel.model.app.Rate;
import com.example.dattienbkhn.travel.model.app.User;
import com.example.dattienbkhn.travel.model.googleinfor.GoogleInfor;
import com.example.dattienbkhn.travel.network.message.WrapperResponse;
import com.example.dattienbkhn.travel.network.message.account.AccRequest;
import com.example.dattienbkhn.travel.network.message.account.AccResponse;
import com.example.dattienbkhn.travel.network.message.user.UserSearch;
import com.example.dattienbkhn.travel.repository.remote.IAccountRemoteDataSource;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by tiendatbkhn on 16/03/2018.
 */

public class AccountRepo implements IAccountRemoteDataSource {
    private IAccountRemoteDataSource mAccRemoteRepo;

    public AccountRepo(IAccountRemoteDataSource mAccRemoteRepo) {
        this.mAccRemoteRepo = mAccRemoteRepo;
    }

    @Override
    public Observable<WrapperResponse<Integer>> checkExistAcc(String userName, String accType) {
        return mAccRemoteRepo.checkExistAcc(userName, accType);
    }

    @Override
    public Observable<WrapperResponse<User>> getUserInfor(int accId) {
        return mAccRemoteRepo.getUserInfor(accId);
    }

    @Override
    public Observable<WrapperResponse<AccResponse>> getAccount(int accId) {
        return mAccRemoteRepo.getAccount(accId);
    }

    @Override
    public Observable<WrapperResponse<Integer>> createAccount(AccRequest accRequest, String userName) {
        return mAccRemoteRepo.createAccount(accRequest, userName);
    }

    @Override
    public Observable<WrapperResponse<Boolean>> changeUserInfor(User userRequest) {
        return mAccRemoteRepo.changeUserInfor(userRequest);
    }

    @Override
    public Observable<WrapperResponse<Boolean>> checkLove(int accId, int placeId) {
        return mAccRemoteRepo.checkLove(accId, placeId);
    }

    @Override
    public Observable<WrapperResponse<Rate>> getUserRateOfplace(int accId, int placeId) {
        return mAccRemoteRepo.getUserRateOfplace(accId, placeId);
    }

    @Override
    public Observable<WrapperResponse<Boolean>> saveLove(Love love, boolean isLove) {
        return mAccRemoteRepo.saveLove(love, isLove);
    }

    @Override
    public Observable<WrapperResponse<Boolean>> saveRate(Rate rate) {
        return mAccRemoteRepo.saveRate(rate);
    }

    @Override
    public Observable<WrapperResponse<Boolean>> postComment(Comment cmt) {
        return mAccRemoteRepo.postComment(cmt);
    }

    @Override
    public Observable<GoogleInfor> getGoogleInfor(String userId) {
        return mAccRemoteRepo.getGoogleInfor(userId);
    }

    @Override
    public Observable<User> postbyte(User userRequest) {
        return mAccRemoteRepo.postbyte(userRequest);
    }

    @Override
    public Observable<WrapperResponse<List<Friend>>> getFriends(int accId) {
        return mAccRemoteRepo.getFriends(accId);
    }

    @Override
    public Observable<WrapperResponse<List<Friend>>> getTopFriends(int accId) {
        return mAccRemoteRepo.getTopFriends(accId);
    }

    @Override
    public Observable<WrapperResponse<List<UserSearch>>> findFriendByName(int accId, String searchname) {
        return mAccRemoteRepo.findFriendByName(accId, searchname);
    }

    @Override
    public Observable<WrapperResponse<Boolean>> getStreamState(int accId) {
        return mAccRemoteRepo.getStreamState(accId);
    }

    @Override
    public Observable<WrapperResponse<Boolean>> saveStreamState(int accId, int streamState) {
        return mAccRemoteRepo.saveStreamState(accId, streamState);
    }

    @Override
    public Observable<WrapperResponse<Boolean>> followFriend(int accId, int friendId,boolean isFollow) {
        return mAccRemoteRepo.followFriend(accId, friendId,isFollow);
    }

    @Override
    public Observable<WrapperResponse<Boolean>> checkFollow(int accId, int friendId) {
        return mAccRemoteRepo.checkFollow(accId, friendId);
    }

    @Override
    public Observable<WrapperResponse<List<String>>> getVOD(int accId) {
        return mAccRemoteRepo.getVOD(accId);
    }

    @Override
    public Observable<WrapperResponse<String>> getThumbnail(String videoName) {
        return mAccRemoteRepo.getThumbnail(videoName);
    }

    @Override
    public Observable<WrapperResponse<Boolean>> makeThumbnail(String videoName) {
        return mAccRemoteRepo.makeThumbnail(videoName);
    }
}
