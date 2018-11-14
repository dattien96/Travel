package com.example.dattienbkhn.travel.network;

import android.app.Application;
import android.support.annotation.NonNull;

import com.example.dattienbkhn.travel.BuildConfig;
import com.example.dattienbkhn.travel.network.adapter.BooleanAdapter;
import com.example.dattienbkhn.travel.network.adapter.IntegerAdapter;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by dattienbkhn on 30/01/2018.
 */

public class RetrofitHelper {
    private static final String TAG = RetrofitHelper.class.getName();

    private static final int CONNECTION_TIMEOUT = 10;

    public static <T> T createService(Application application, String endPoint, Class<T> serviceClass) {
        return createService(application, endPoint, serviceClass, getGsonConfig(), null);
    }

    public static <T> T createService(Application application, String endPoint, Class<T> serviceClass,
                                      Interceptor interceptor) {
        return createService(application, endPoint, serviceClass, getGsonConfig(), interceptor);
    }

    private static <T> T createService(Application application, String endPoint,
                                       Class<T> serviceClass, @NonNull Gson gson, Interceptor interceptor) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        int cacheSize = 30 * 1024 * 1024; // 10 MiB
        httpClientBuilder.cache(new Cache(application.getCacheDir(), cacheSize));
        if (interceptor != null) {
            ///clear all interceptors before add new
            httpClientBuilder.networkInterceptors().clear();
            httpClientBuilder.addInterceptor(interceptor);
        }
        httpClientBuilder.readTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(endPoint)
                //.addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson));

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            httpClientBuilder.addInterceptor(logging);
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        }

        Retrofit retrofit = builder.client(httpClientBuilder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        return retrofit.create(serviceClass);
    }

    static Gson getGsonConfig() {
        BooleanAdapter booleanAdapter = new BooleanAdapter();
        IntegerAdapter integerAdapter = new IntegerAdapter();
        return new GsonBuilder().registerTypeAdapter(Boolean.class, booleanAdapter)
                .registerTypeAdapter(boolean.class, booleanAdapter)
                .registerTypeAdapter(Integer.class, integerAdapter)
                .registerTypeAdapter(int.class, integerAdapter)
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }
}
