package com.example.dattienbkhn.travel.repository;

import com.example.dattienbkhn.travel.model.app.City;
import com.example.dattienbkhn.travel.network.message.WrapperResponse;
import com.example.dattienbkhn.travel.repository.local.ICityLocalDataSource;
import com.example.dattienbkhn.travel.repository.remote.ICityRemoteDataSource;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by dattienbkhn on 08/02/2018.
 */

public class CityRepo implements ICityLocalDataSource,ICityRemoteDataSource {
    private ICityLocalDataSource mCityLocalRepo;
    private ICityRemoteDataSource mCityRemoteRepo;

    public CityRepo(ICityRemoteDataSource mCityRemoteRepo) {
        this.mCityRemoteRepo = mCityRemoteRepo;
    }

    @Override
    public Observable<WrapperResponse<List<City>>> getTopCities() {
        return mCityRemoteRepo.getTopCities();
    }

    @Override
    public Observable<WrapperResponse<Integer>> getCityId(int id) {
        return mCityRemoteRepo.getCityId(id);
    }

    @Override
    public Observable<WrapperResponse<List<City>>> findCityByName(String cityName, String regionName) {
        return mCityRemoteRepo.findCityByName(cityName, regionName);
    }

    @Override
    public Observable<WrapperResponse<Integer>> increaseCityViews(int cityId) {
        return mCityRemoteRepo.increaseCityViews(cityId);
    }
}
