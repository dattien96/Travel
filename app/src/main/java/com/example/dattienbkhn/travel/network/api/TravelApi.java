package com.example.dattienbkhn.travel.network.api;

import com.example.dattienbkhn.travel.model.app.City;
import com.example.dattienbkhn.travel.model.app.Comment;
import com.example.dattienbkhn.travel.model.app.Friend;
import com.example.dattienbkhn.travel.model.app.Image;
import com.example.dattienbkhn.travel.model.app.Love;
import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.model.app.PlaceInfor;
import com.example.dattienbkhn.travel.model.app.Rate;
import com.example.dattienbkhn.travel.model.app.User;
import com.example.dattienbkhn.travel.network.message.WrapperResponse;
import com.example.dattienbkhn.travel.network.message.account.AccRequest;
import com.example.dattienbkhn.travel.network.message.account.AccResponse;
import com.example.dattienbkhn.travel.network.message.user.UserSearch;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by dattienbkhn on 06/02/2018.
 */

public interface TravelApi {
    @GET("getCollapseImage")
    Observable<WrapperResponse<String>> getCollapseImage();

    @GET("getPlaceById")
    Observable<WrapperResponse<Place>>
    getPlaceById(@Query("placeid") int placeId);

    @GET("getFamousPlaceByCountry")
    Observable<WrapperResponse<List<Place>>>
    getFamousPlaceByCountry(@Query("countryname") String name);

    @GET("getTopCities")
    Observable<WrapperResponse<List<City>>>
    getTopCities();

    @GET("getFamousPlaceByCity")
    Observable<WrapperResponse<List<Place>>>
    getFamousPlace(@Query("cityid") int idCity, @Query("grouptype") int groupType);

    @GET("getAllPlaceByType")
    Observable<WrapperResponse<List<Place>>>
    getAllPlace(@Query("cityid") int idCity, @Query("placetype") String placeType);


    @GET("getCityImageAlbum")
    Observable<WrapperResponse<List<Image>>>
    getImagesOfCity(@Query("cityid") int id);

    @GET("getPlaceImageAlbum")
    Observable<WrapperResponse<List<Image>>>
    getImagesOfPlace(@Query("placeid") int id);

    @GET("getDetailsInforOfPlace")
    Observable<WrapperResponse<PlaceInfor>>
    getDetailsInforOfPlace(@Query("addressid") int id);

    @GET("getCityId")
    Observable<WrapperResponse<Integer>>
    getCityId(@Query("placeid") int id);

    @GET("findPlaceByName")
    Observable<WrapperResponse<List<Place>>>
    findPlaceByName(@Query("placename") String placeName, @Query("regionname") String regionName);

    @GET("findCityByName")
    Observable<WrapperResponse<List<City>>>
    findCityByName(@Query("cityname") String cityName, @Query("regionname") String regionName);

    @GET("checkExistAcc")
    Observable<WrapperResponse<Integer>>
    checkExistAcc(@Query("username") String userName, @Query("acctype") String accType);

    @GET("getUserInfor")
    Observable<WrapperResponse<User>>
    getUserInfor(@Query("accid") int accId);

    @GET("getAccount")
    Observable<WrapperResponse<AccResponse>>
    getAccount(@Query("accid") int accId);

    @POST("createAccount")
    Observable<WrapperResponse<Integer>>
    createAccount(@Body AccRequest accRequest, @Query("username") String userName);

    @POST("changeUserInfor")
    Observable<WrapperResponse<Boolean>>
    changeUserInfor(@Body User userRequest);

    @GET("checkLove")
    Observable<WrapperResponse<Boolean>>
    checkLove(@Query("accid") int accId, @Query("placeid") int placeId);

    @GET("getUserRateOfplace")
    Observable<WrapperResponse<Rate>>
    getUserRateOfplace(@Query("accid") int accId, @Query("placeid") int placeId);

    @GET("getPlaceRate")
    Observable<WrapperResponse<Double>>
    getPlaceRate(@Query("placeid") int placeId);

    @POST("saveLove")
    Observable<WrapperResponse<Boolean>>
    saveLove(@Body Love love, @Query("islove") boolean isLove);

    @POST("saveRate")
    Observable<WrapperResponse<Boolean>>
    saveRate(@Body Rate rate);

    @GET("increasePlaceViews")
    Observable<WrapperResponse<Integer>>
    increasePlaceViews(@Query("placeid") int placeId);

    @GET("increaseCityViews")
    Observable<WrapperResponse<Integer>>
    increaseCityViews(@Query("cityid") int cityId);

    @GET("getComments")
    Observable<WrapperResponse<List<Comment>>>
    getComments(@Query("placeid") int placeId);

    @GET("getTopComments")
    Observable<WrapperResponse<List<Comment>>>
    getTopComments(@Query("placeid") int placeId);

    @POST("postComment")
    Observable<WrapperResponse<Boolean>>
    postComment(@Body Comment cmt);

    @POST("postbyte")
    Observable<User>
    postbyte(@Body User userRequest);

    @GET("getFriends")
    Observable<WrapperResponse<List<Friend>>>
    getFriends(@Query("accid") int accId);

    @GET("getTopFriends")
    Observable<WrapperResponse<List<Friend>>>
    getTopFriends(@Query("accid") int accId);

    @GET("findFriendByName")
    Observable<WrapperResponse<List<UserSearch>>>
    findFriendByName(@Query("accid") int accId,@Query("searchname") String searchname);

    @GET("getStreamState")
    Observable<WrapperResponse<Boolean>>
    getStreamState(@Query("accid") int accId);

    @GET("saveStreamState")
    Observable<WrapperResponse<Boolean>>
    saveStreamState(@Query("accid") int accId, @Query("streamstate") int streamState);

    @GET("followFriend")
    Observable<WrapperResponse<Boolean>>
    followFriend(@Query("accid") int accId, @Query("friendid") int friendId,@Query("isfollow") boolean isFollow);

    @GET("checkFollow")
    Observable<WrapperResponse<Boolean>>
    checkFollow(@Query("accid") int accId, @Query("friendid") int friendId);

    @GET("getLovedPlaceByUser")
    Observable<WrapperResponse<List<Place>>>
    getLovedPlaceByUser(@Query("accid") int accId);

    @GET("getVOD")
    Observable<WrapperResponse<List<String>>>
    getVOD(@Query("accid") int accId);

    @GET("getThumbnail")
    Observable<WrapperResponse<String>>
    getThumbnail(@Query("videoname") String videoName);

    @GET("makeThumbnail")
    Observable<WrapperResponse<Boolean>>
    makeThumbnail(@Query("videoname") String videoName);

}
