package com.example.dattienbkhn.travel.repository;

import com.example.dattienbkhn.travel.repository.local.ICountryLocalDataSource;
import com.example.dattienbkhn.travel.repository.remote.ICountryRemoteDataSource;

/**
 * Created by dattienbkhn on 07/02/2018.
 */

public class CountryRepo implements ICountryLocalDataSource,ICountryRemoteDataSource {
    private ICountryLocalDataSource mLocalCountryRepo;
    private ICountryRemoteDataSource mRemoteCountryRepo;

    public CountryRepo( ICountryRemoteDataSource mRemoteCountryRepo) {
        //this.mLocalCountryRepo = mLocalCountryRepo;
        this.mRemoteCountryRepo = mRemoteCountryRepo;
    }


}
