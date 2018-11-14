package com.example.dattienbkhn.travel.network.service;

import android.app.Application;
import android.support.annotation.NonNull;

import com.example.dattienbkhn.travel.network.RetrofitHelper;
import com.example.dattienbkhn.travel.network.api.IpApi;
import com.example.dattienbkhn.travel.utils.Constant;

/**
 * Created by dattienbkhn on 03/03/2018.
 */

public class IpService {
    private static IpApi mIpApi;

    public static void initIpService(@NonNull Application app) {
        // SharedPrefsApi prefsApi = SharedPrefsImpl.getInstance();
        // String token = prefsApi.get(SharedPrefsKey.TOKEN_KEY, String.class);
        //RetrofitInterceptor interceptor = null;
        // if (!TextUtils.isEmpty(token)) {
        //    interceptor = new RetrofitInterceptor(token);
        //}
        mIpApi = RetrofitHelper.createService(app, Constant.IP_API_BASE_URL, IpApi.class);
    }

    public static IpApi getIpApiInstance(){
        if (mIpApi == null) {
            throw new RuntimeException("Need call method FOrderServiceClient#initialize() first");
        }
        return mIpApi;
    }
}
