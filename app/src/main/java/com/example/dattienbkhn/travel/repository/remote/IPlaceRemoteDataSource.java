package com.example.dattienbkhn.travel.repository.remote;

import com.example.dattienbkhn.travel.model.app.Comment;
import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.model.app.PlaceInfor;
import com.example.dattienbkhn.travel.network.message.WrapperResponse;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by dattienbkhn on 07/02/2018.
 */

public interface IPlaceRemoteDataSource {
    Observable<WrapperResponse<List<Place>>>
    getFamousPlaceByCountry(String countryname);

    Observable<WrapperResponse<Place>>
    getPlaceById(int placeId);

    Observable<WrapperResponse<PlaceInfor>>
    getPlaceDetailsInfor(int id);

    Observable<WrapperResponse<List<Place>>>
    getFamousPlace(int idCity, int groupType);

    Observable<WrapperResponse<List<Place>>>
    getAllPlace(int idCity, String placeType);

    Observable<WrapperResponse<List<Place>>>
    findPlaceByName(String placeName, String regionName);

    Observable<WrapperResponse<Double>>
    getPlaceRate(int placeId);

    Observable<WrapperResponse<Integer>>
    increasePlaceViews(int placeId);

    Observable<WrapperResponse<List<Comment>>>
    getComments(int placeId);

    Observable<WrapperResponse<List<Comment>>>
    getTopComments(int placeId);

    Observable<WrapperResponse<List<Place>>>
    getLovedPlaceByUser(int accId);
}
