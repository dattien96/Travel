package com.example.dattienbkhn.travel.repository.remote;

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

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;

/**
 * Created by tiendatbkhn on 16/03/2018.
 */

public interface IAccountRemoteDataSource {

    Observable<WrapperResponse<Integer>>
    checkExistAcc(String userName, String accType);


    Observable<WrapperResponse<User>>
    getUserInfor(int accId);

    Observable<WrapperResponse<AccResponse>>
    getAccount(int accId);


    Observable<WrapperResponse<Integer>>
    createAccount(AccRequest accRequest, String userName);


    Observable<WrapperResponse<Boolean>>
    changeUserInfor(User userRequest);


    Observable<WrapperResponse<Boolean>>
    checkLove(int accId, int placeId);


    Observable<WrapperResponse<Rate>>
    getUserRateOfplace(int accId, int placeId);


    Observable<WrapperResponse<Boolean>>
    saveLove(Love love, boolean isLove);


    Observable<WrapperResponse<Boolean>>
    saveRate(Rate rate);

    Observable<WrapperResponse<Boolean>>
    postComment(Comment cmt);

    Observable<GoogleInfor> getGoogleInfor(String userId);

    Observable<User>
    postbyte(@Body User userRequest);

    Observable<WrapperResponse<List<Friend>>>
    getFriends(int accId);

    Observable<WrapperResponse<List<Friend>>>
    getTopFriends( int accId);

    Observable<WrapperResponse<List<UserSearch>>>
    findFriendByName(int accId, String searchname);

    Observable<WrapperResponse<Boolean>>
    getStreamState(int accId);

    Observable<WrapperResponse<Boolean>>
    saveStreamState(int accId,int streamState);

    Observable<WrapperResponse<Boolean>>
    followFriend(int accId,int friendId,boolean isFollow);

    Observable<WrapperResponse<Boolean>>
    checkFollow(int accId,int friendId);

    Observable<WrapperResponse<List<String>>>
    getVOD(int accId);

    Observable<WrapperResponse<String>>
    getThumbnail( String videoName);

    Observable<WrapperResponse<Boolean>>
    makeThumbnail( String videoName);
}
