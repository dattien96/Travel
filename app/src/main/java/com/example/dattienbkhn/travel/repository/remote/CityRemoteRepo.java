package com.example.dattienbkhn.travel.repository.remote;

import com.example.dattienbkhn.travel.model.app.City;
import com.example.dattienbkhn.travel.network.api.TravelApi;
import com.example.dattienbkhn.travel.network.message.WrapperResponse;
import com.example.dattienbkhn.travel.network.service.TravelService;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by dattienbkhn on 08/02/2018.
 */

public class CityRemoteRepo implements ICityRemoteDataSource {
    private static TravelApi mApi;

    public CityRemoteRepo() {
        mApi = TravelService.getTravelApiInstance();
    }

    @Override
    public Observable<WrapperResponse<List<City>>> getTopCities() {
        return mApi.getTopCities();
    }

    @Override
    public Observable<WrapperResponse<Integer>> getCityId(int id) {
        return mApi.getCityId(id);
    }

    @Override
    public Observable<WrapperResponse<List<City>>> findCityByName(String cityName, String regionName) {
        return mApi.findCityByName(cityName, regionName);
    }

    @Override
    public Observable<WrapperResponse<Integer>> increaseCityViews(int cityId) {
        return mApi.increaseCityViews(cityId);
    }


}
