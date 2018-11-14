package com.example.dattienbkhn.travel.repository.remote;

import android.util.Log;

import com.example.dattienbkhn.travel.model.facebook.fbcover.CoverWrap;
import com.example.dattienbkhn.travel.model.ip.IpRes;
import com.example.dattienbkhn.travel.model.location.CurrentLocation;
import com.example.dattienbkhn.travel.model.map.nearby.NearByWrapp;
import com.example.dattienbkhn.travel.model.weather.currentweather.WeatherObj;
import com.example.dattienbkhn.travel.model.weather.forecast.ForecastObj;
import com.example.dattienbkhn.travel.network.api.FacebookApi;
import com.example.dattienbkhn.travel.network.api.IpApi;
import com.example.dattienbkhn.travel.network.api.LocationApi;
import com.example.dattienbkhn.travel.network.api.MapApi;
import com.example.dattienbkhn.travel.network.api.TravelApi;
import com.example.dattienbkhn.travel.network.api.WeatherApi;
import com.example.dattienbkhn.travel.network.message.WrapperResponse;
import com.example.dattienbkhn.travel.network.message.map.DirectionResponse;
import com.example.dattienbkhn.travel.network.service.FacebookService;
import com.example.dattienbkhn.travel.network.service.IpService;
import com.example.dattienbkhn.travel.network.service.LocationService;
import com.example.dattienbkhn.travel.network.service.MapService;
import com.example.dattienbkhn.travel.network.service.TravelService;
import com.example.dattienbkhn.travel.network.service.WeatherService;

import io.reactivex.Observable;

/**
 * Created by dattienbkhn on 07/02/2018.
 */

public class CommonRemoteRepo implements ICommonRemoteDataSource {
    private static TravelApi mApi;
    private static IpApi mIpApi;
    private static LocationApi mLoApi;
    private static MapApi mMapApi;
    private static WeatherApi mWeatherApi;
    private static FacebookApi mFbApi;

    public CommonRemoteRepo() {
        mApi = TravelService.getTravelApiInstance();
        mIpApi = IpService.getIpApiInstance();
        mLoApi = LocationService.getLocationApiInstance();
        mMapApi = MapService.getMapApiInstance();
        mWeatherApi = WeatherService.getWeatherApiInstance();
        mFbApi = FacebookService.getFacebookApiInstance();
    }


    @Override
    public Observable<WrapperResponse<String>> getCollapseImageUrl() {
        return mApi.getCollapseImage();
    }

    @Override
    public Observable<IpRes> getCurrentIpAddress() {
        return mIpApi.getCurrentIpAddress();
    }

    @Override
    public Observable<CurrentLocation> getCurrentLocation(String ip) {
        return mLoApi.getCurrentLocation(ip);
    }

    @Override
    public Observable<DirectionResponse> getDirection(String origin, String destination) {
        return mMapApi.getDirection(origin,destination);
    }

    @Override
    public Observable<NearByWrapp> getNearbyPlaces(String type, String location, int radius) {
        return mMapApi.getNearbyPlaces(type, location, radius);
    }

    @Override
    public Observable<WeatherObj> getWeather(String cityName) {
        Log.e("wea",""+cityName);
        return mWeatherApi.getWeather(cityName);
    }

    @Override
    public Observable<ForecastObj> getForecast(String cityName) {
        return mWeatherApi.getForecast(cityName);
    }

    @Override
    public Observable<CoverWrap> getCoverFbImage(String userid, String token) {
        return mFbApi.getCoverFbImage(userid, token);
    }
}
