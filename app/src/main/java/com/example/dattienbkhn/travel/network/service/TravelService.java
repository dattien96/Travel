package com.example.dattienbkhn.travel.network.service;

import android.app.Application;
import android.support.annotation.NonNull;

import com.example.dattienbkhn.travel.network.RetrofitHelper;
import com.example.dattienbkhn.travel.network.api.TravelApi;
import com.example.dattienbkhn.travel.utils.Constant;

/**
 * Created by dattienbkhn on 06/02/2018.
 */

public class TravelService {
    private static TravelApi mTravelApi;

    public static void initTravelService(@NonNull Application app) {
        // SharedPrefsApi prefsApi = SharedPrefsImpl.getInstance();
        // String token = prefsApi.get(SharedPrefsKey.TOKEN_KEY, String.class);
        //RetrofitInterceptor interceptor = null;
        // if (!TextUtils.isEmpty(token)) {
        //    interceptor = new RetrofitInterceptor(token);
        //}
        mTravelApi = RetrofitHelper.createService(app, Constant.TRAVEL_API_BASE_URL, TravelApi.class);
    }

    public static TravelApi getTravelApiInstance(){
        if (mTravelApi == null) {
            throw new RuntimeException("Need call method FOrderServiceClient#initialize() first");
        }
        return mTravelApi;
    }
}
