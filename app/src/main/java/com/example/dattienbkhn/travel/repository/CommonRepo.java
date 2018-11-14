package com.example.dattienbkhn.travel.repository;

import com.example.dattienbkhn.travel.model.facebook.fbcover.CoverWrap;
import com.example.dattienbkhn.travel.model.ip.IpRes;
import com.example.dattienbkhn.travel.model.location.CurrentLocation;
import com.example.dattienbkhn.travel.model.map.nearby.NearByWrapp;
import com.example.dattienbkhn.travel.model.weather.currentweather.WeatherObj;
import com.example.dattienbkhn.travel.model.weather.forecast.ForecastObj;
import com.example.dattienbkhn.travel.network.message.WrapperResponse;
import com.example.dattienbkhn.travel.network.message.map.DirectionResponse;
import com.example.dattienbkhn.travel.repository.local.ICommonLocalDataSource;
import com.example.dattienbkhn.travel.repository.remote.ICommonRemoteDataSource;

import io.reactivex.Observable;

/**
 * Created by dattienbkhn on 07/02/2018.
 */

public class CommonRepo implements ICommonLocalDataSource,ICommonRemoteDataSource {
    private ICommonLocalDataSource mLocalCommonRepo;
    private ICommonRemoteDataSource mRemoteCommonRepo;

    public CommonRepo( ICommonRemoteDataSource mRemoteCommonRepo) {
        //this.mLocalCommonRepo = mLocalCommonRepo;
        this.mRemoteCommonRepo = mRemoteCommonRepo;
    }

    @Override
    public Observable<WrapperResponse<String>> getCollapseImageUrl() {
        return mRemoteCommonRepo.getCollapseImageUrl();
    }

    @Override
    public Observable<IpRes> getCurrentIpAddress() {
        return mRemoteCommonRepo.getCurrentIpAddress();
    }

    @Override
    public Observable<CurrentLocation> getCurrentLocation(String ip) {
        return mRemoteCommonRepo.getCurrentLocation(ip);
    }

    @Override
    public Observable<DirectionResponse> getDirection(String origin, String destination) {
        return mRemoteCommonRepo.getDirection(origin, destination);
    }

    @Override
    public Observable<NearByWrapp> getNearbyPlaces(String type, String location, int radius) {
        return mRemoteCommonRepo.getNearbyPlaces(type, location, radius);
    }

    @Override
    public Observable<WeatherObj> getWeather(String cityName) {
        return mRemoteCommonRepo.getWeather(cityName);
    }

    @Override
    public Observable<ForecastObj> getForecast(String cityName) {
        return mRemoteCommonRepo.getForecast(cityName);
    }

    @Override
    public Observable<CoverWrap> getCoverFbImage(String userid, String token) {
        return mRemoteCommonRepo.getCoverFbImage(userid, token);
    }
}
