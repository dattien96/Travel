package com.example.dattienbkhn.travel.network.service;

import android.app.Application;
import android.support.annotation.NonNull;

import com.example.dattienbkhn.travel.network.RetrofitHelper;
import com.example.dattienbkhn.travel.network.api.GoogleInforApi;
import com.example.dattienbkhn.travel.utils.Constant;

/**
 * Created by tiendatbkhn on 20/03/2018.
 */

public class GoogleInforService {
    private static GoogleInforApi mGgApi;

    public static void initGoogleInforService(@NonNull Application app) {
        // SharedPrefsApi prefsApi = SharedPrefsImpl.getInstance();
        // String token = prefsApi.get(SharedPrefsKey.TOKEN_KEY, String.class);
        //RetrofitInterceptor interceptor = null;
        // if (!TextUtils.isEmpty(token)) {
        //    interceptor = new RetrofitInterceptor(token);
        //}
        mGgApi = RetrofitHelper.createService(app, Constant.GOOGLE_INFOR_API_BASE_URL, GoogleInforApi.class);
    }

    public static GoogleInforApi getGoogleInforApiInstance(){
        if (mGgApi == null) {
            throw new RuntimeException("Need call method FOrderServiceClient#initialize() first");
        }
        return mGgApi;
    }
}
