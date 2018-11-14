package com.example.dattienbkhn.travel.repository.remote;

import com.example.dattienbkhn.travel.model.app.City;
import com.example.dattienbkhn.travel.network.message.WrapperResponse;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by dattienbkhn on 08/02/2018.
 */

public interface ICityRemoteDataSource {
    Observable<WrapperResponse<List<City>>>
    getTopCities();

    Observable<WrapperResponse<Integer>>
    getCityId(int id);

    Observable<WrapperResponse<List<City>>>
    findCityByName(String cityName, String regionName);

    Observable<WrapperResponse<Integer>>
    increaseCityViews(int cityId);
}
