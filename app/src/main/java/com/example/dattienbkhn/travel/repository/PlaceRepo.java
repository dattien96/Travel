package com.example.dattienbkhn.travel.repository;

import com.example.dattienbkhn.travel.model.app.Comment;
import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.model.app.PlaceInfor;
import com.example.dattienbkhn.travel.network.message.WrapperResponse;
import com.example.dattienbkhn.travel.repository.local.IPlaceLocalDataSource;
import com.example.dattienbkhn.travel.repository.remote.IPlaceRemoteDataSource;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by dattienbkhn on 07/02/2018.
 */

public class PlaceRepo implements IPlaceRemoteDataSource,IPlaceLocalDataSource {
    private IPlaceLocalDataSource mLocalPlaceRepo;
    private IPlaceRemoteDataSource mRemotePlaceRepo;

    public PlaceRepo( IPlaceRemoteDataSource mRemotePlaceRepoRepo) {

        this.mRemotePlaceRepo = mRemotePlaceRepoRepo;
    }
    @Override
    public Observable<WrapperResponse<List<Place>>> getFamousPlaceByCountry(String countryname) {
        return mRemotePlaceRepo.getFamousPlaceByCountry(countryname);
    }

    @Override
    public Observable<WrapperResponse<Place>> getPlaceById(int placeId) {
        return mRemotePlaceRepo.getPlaceById(placeId);
    }

    @Override
    public Observable<WrapperResponse<PlaceInfor>> getPlaceDetailsInfor(int addresId) {
        return mRemotePlaceRepo.getPlaceDetailsInfor(addresId);
    }

    @Override
    public Observable<WrapperResponse<List<Place>>> getFamousPlace(int idCity, int groupType) {
        return mRemotePlaceRepo.getFamousPlace(idCity, groupType);
    }

    @Override
    public Observable<WrapperResponse<List<Place>>> getAllPlace(int idCity, String placeType) {
        return mRemotePlaceRepo.getAllPlace(idCity, placeType);
    }

    @Override
    public Observable<WrapperResponse<List<Place>>> findPlaceByName(String placeName, String regionName) {
        return mRemotePlaceRepo.findPlaceByName(placeName, regionName);
    }

    @Override
    public Observable<WrapperResponse<Double>> getPlaceRate(int placeId) {
        return mRemotePlaceRepo.getPlaceRate(placeId);
    }

    @Override
    public Observable<WrapperResponse<Integer>> increasePlaceViews(int placeId) {
        return mRemotePlaceRepo.increasePlaceViews(placeId);
    }

    @Override
    public Observable<WrapperResponse<List<Comment>>> getComments(int placeId) {
        return mRemotePlaceRepo.getComments(placeId);
    }

    @Override
    public Observable<WrapperResponse<List<Comment>>> getTopComments(int placeId) {
        return mRemotePlaceRepo.getTopComments(placeId);
    }

    @Override
    public Observable<WrapperResponse<List<Place>>> getLovedPlaceByUser(int accId) {
        return mRemotePlaceRepo.getLovedPlaceByUser(accId);
    }


    /*@Override
    public Flowable<List<PlaceRoom>> getPlacesSave() {
        return mLocalPlaceRepo.getPlacesSave();
    }

    @Override
    public long insertPlace(PlaceRoom place) {
        return mLocalPlaceRepo.insertPlace(place);
    }

    @Override
    public void deleteAllPlacesSave() {
        mLocalPlaceRepo.deleteAllPlacesSave();
    }

    @Override
    public void deletePlaceSave(int placeId) {
        mLocalPlaceRepo.deletePlaceSave(placeId);
    }

    @Override
    public void updatePlaceSave(PlaceRoom placeRoom) {
        mLocalPlaceRepo.updatePlaceSave(placeRoom);
    }*/
}
