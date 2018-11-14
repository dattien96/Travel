package com.example.dattienbkhn.travel.repository.remote;

import com.example.dattienbkhn.travel.model.facebook.fbcover.CoverWrap;
import com.example.dattienbkhn.travel.model.ip.IpRes;
import com.example.dattienbkhn.travel.model.location.CurrentLocation;
import com.example.dattienbkhn.travel.model.map.nearby.NearByWrapp;
import com.example.dattienbkhn.travel.model.weather.currentweather.WeatherObj;
import com.example.dattienbkhn.travel.model.weather.forecast.ForecastObj;
import com.example.dattienbkhn.travel.network.message.WrapperResponse;
import com.example.dattienbkhn.travel.network.message.map.DirectionResponse;

import io.reactivex.Observable;

/**
 * Created by dattienbkhn on 07/02/2018.
 */

public interface ICommonRemoteDataSource {
    Observable<WrapperResponse<String>> getCollapseImageUrl();

    //ip api
    Observable<IpRes> getCurrentIpAddress();

    //location api
    Observable<CurrentLocation> getCurrentLocation(String ip);

    //map api
    Observable<DirectionResponse> getDirection(String origin, String destination);

    Observable<NearByWrapp> getNearbyPlaces(String type, String location, int radius);

    //weather api

    Observable<WeatherObj> getWeather(String cityName);

    Observable<ForecastObj> getForecast(String cityName);


    //facebook api
    Observable<CoverWrap> getCoverFbImage(String userid, String token);
}
