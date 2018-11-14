package com.example.dattienbkhn.travel.network.service;

import android.app.Application;
import android.support.annotation.NonNull;

import com.example.dattienbkhn.travel.network.RetrofitHelper;
import com.example.dattienbkhn.travel.network.api.FacebookApi;
import com.example.dattienbkhn.travel.utils.Constant;

/**
 * Created by dattienbkhn on 09/03/2018.
 */

public class FacebookService {
    private static FacebookApi mFbApi;

    public static void initFacebookService(@NonNull Application app) {
        // SharedPrefsApi prefsApi = SharedPrefsImpl.getInstance();
        // String token = prefsApi.get(SharedPrefsKey.TOKEN_KEY, String.class);
        //RetrofitInterceptor interceptor = null;
        // if (!TextUtils.isEmpty(token)) {
        //    interceptor = new RetrofitInterceptor(token);
        //}
        mFbApi = RetrofitHelper.createService(app, Constant.FACEBOOK_API_BASE_URL, FacebookApi.class);
    }

    public static FacebookApi getFacebookApiInstance(){
        if (mFbApi == null) {
            throw new RuntimeException("Need call method FOrderServiceClient#initialize() first");
        }
        return mFbApi;
    }
}
