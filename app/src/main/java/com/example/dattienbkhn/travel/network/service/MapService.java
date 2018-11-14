package com.example.dattienbkhn.travel.network.service;

import android.app.Application;
import android.support.annotation.NonNull;

import com.example.dattienbkhn.travel.network.RetrofitHelper;
import com.example.dattienbkhn.travel.network.api.MapApi;
import com.example.dattienbkhn.travel.utils.Constant;

/**
 * Created by dattienbkhn on 06/02/2018.
 */

public class MapService {
    private static MapApi mMapApi;

    public static void initMapService(@NonNull Application app) {
        // SharedPrefsApi prefsApi = SharedPrefsImpl.getInstance();
        // String token = prefsApi.get(SharedPrefsKey.TOKEN_KEY, String.class);
        //RetrofitInterceptor interceptor = null;
        // if (!TextUtils.isEmpty(token)) {
        //    interceptor = new RetrofitInterceptor(token);
        //}
        mMapApi = RetrofitHelper.createService(app, Constant.MAP_API_BASE_URL, MapApi.class);
    }

    public static MapApi getMapApiInstance(){
        if (mMapApi == null) {
            throw new RuntimeException("Need call method FOrderServiceClient#initialize() first");
        }
        return mMapApi;
    }
}
