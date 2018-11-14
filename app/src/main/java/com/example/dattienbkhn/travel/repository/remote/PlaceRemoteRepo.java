package com.example.dattienbkhn.travel.repository.remote;

import com.example.dattienbkhn.travel.model.app.Comment;
import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.model.app.PlaceInfor;
import com.example.dattienbkhn.travel.network.api.TravelApi;
import com.example.dattienbkhn.travel.network.message.WrapperResponse;
import com.example.dattienbkhn.travel.network.service.TravelService;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by dattienbkhn on 07/02/2018.
 */

public class PlaceRemoteRepo implements IPlaceRemoteDataSource {
    private TravelApi mApi;
    public PlaceRemoteRepo() {
        mApi = TravelService.getTravelApiInstance();
    }
    @Override
    public Observable<WrapperResponse<List<Place>>> getFamousPlaceByCountry(String countryname) {
        return mApi.getFamousPlaceByCountry(countryname);
    }

    @Override
    public Observable<WrapperResponse<Place>> getPlaceById(int placeId) {
        return mApi.getPlaceById(placeId);
    }

    @Override
    public Observable<WrapperResponse<PlaceInfor>> getPlaceDetailsInfor(int id) {
        return mApi.getDetailsInforOfPlace(id);
    }

    @Override
    public Observable<WrapperResponse<List<Place>>> getFamousPlace(int idCity, int groupType) {
        return mApi.getFamousPlace(idCity,groupType);
    }

    @Override
    public Observable<WrapperResponse<List<Place>>> getAllPlace(int idCity, String placeType) {
        return mApi.getAllPlace(idCity, placeType);
    }

    @Override
    public Observable<WrapperResponse<List<Place>>> findPlaceByName(String placeName, String regionName) {
        return mApi.findPlaceByName(placeName, regionName);
    }

    @Override
    public Observable<WrapperResponse<Double>> getPlaceRate(int placeId) {
        return mApi.getPlaceRate(placeId);
    }

    @Override
    public Observable<WrapperResponse<Integer>> increasePlaceViews(int placeId) {
        return mApi.increasePlaceViews(placeId);
    }

    @Override
    public Observable<WrapperResponse<List<Comment>>> getComments(int placeId) {
        return mApi.getComments(placeId);
    }

    @Override
    public Observable<WrapperResponse<List<Comment>>> getTopComments(int placeId) {
        return mApi.getTopComments(placeId);
    }

    @Override
    public Observable<WrapperResponse<List<Place>>> getLovedPlaceByUser(int accId) {
        return mApi.getLovedPlaceByUser(accId);
    }


}
