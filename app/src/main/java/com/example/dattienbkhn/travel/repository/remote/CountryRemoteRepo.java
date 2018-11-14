package com.example.dattienbkhn.travel.repository.remote;

import com.example.dattienbkhn.travel.network.api.TravelApi;
import com.example.dattienbkhn.travel.network.service.TravelService;

/**
 * Created by dattienbkhn on 07/02/2018.
 */

public class CountryRemoteRepo implements ICountryRemoteDataSource {
    private TravelApi mApi;

    public CountryRemoteRepo() {
        mApi = TravelService.getTravelApiInstance();
    }


}
