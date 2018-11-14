package com.example.dattienbkhn.travel.repository;

import android.app.Application;

import com.example.dattienbkhn.travel.repository.remote.AccountRemoteRepo;
import com.example.dattienbkhn.travel.repository.remote.CityRemoteRepo;
import com.example.dattienbkhn.travel.repository.remote.CommonRemoteRepo;
import com.example.dattienbkhn.travel.repository.remote.CountryRemoteRepo;
import com.example.dattienbkhn.travel.repository.remote.ImageRemoteRepo;
import com.example.dattienbkhn.travel.repository.remote.PlaceRemoteRepo;

/**
 * Created by dattienbkhn on 07/02/2018.
 */

public class AppRepository {

    private static CommonRepo mCommonRepo;
    private static CountryRepo mCountryRepo;
    private static PlaceRepo mPlaceRepo;
    private static CityRepo mCityRepo;
    private static ImageRepo mImageRepo;
    private static AccountRepo mAccountRepo;

    public static void createAllRepo(Application application) {
        //get room instance
        //TravelRoom room = TravelRoom.getRoom(application);


        //Common Repo
        // CommonLocalRepo commonLocalRepo =
        //  new CommonLocalRepo(room.getCommonDao());
        CommonRemoteRepo commonRemoteRepo =
                new CommonRemoteRepo();
        mCommonRepo = new CommonRepo(commonRemoteRepo);

        //country Repo
        // CountryLocalRepo countryLocalRepo =
        //new CountryLocalRepo(room.getCountryDao());
        CountryRemoteRepo countryRemoteRepo =
                new CountryRemoteRepo();
        mCountryRepo = new CountryRepo(countryRemoteRepo);

        //place repo
        //PlaceLocalRepo placeLocalRepo = new PlaceLocalRepo(room.getPlaceDao());
        PlaceRemoteRepo placeRemoteRepo = new PlaceRemoteRepo();
        mPlaceRepo = new PlaceRepo(placeRemoteRepo);

        //city repo
        //CityLocalRepo cityLocalRepo = new CityLocalRepo(room.getCityDao());
        CityRemoteRepo cityRemoteRepo = new CityRemoteRepo();
        mCityRepo = new CityRepo(cityRemoteRepo);

        //image repo
        //ImageLocalRepo imageLocalRepo = new ImageLocalRepo(room.getImageDao());
        ImageRemoteRepo imageRemoteRepo = new ImageRemoteRepo();
        mImageRepo = new ImageRepo(imageRemoteRepo);

        //acc repo
        AccountRemoteRepo accountRemoteRepo = new AccountRemoteRepo();
        mAccountRepo = new AccountRepo(accountRemoteRepo);

    }

    public static CommonRepo getCommonRepo() {
        return mCommonRepo;
    }

    public static CountryRepo getCountryRepo() {
        return mCountryRepo;
    }

    public static PlaceRepo getPlaceRepo() {
        return mPlaceRepo;
    }

    public static CityRepo getCityRepo() {
        return mCityRepo;
    }

    public static ImageRepo getImageRepo() {
        return mImageRepo;
    }

    public static AccountRepo getAccountRepo() {
        return mAccountRepo;
    }
}
