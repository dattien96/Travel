package com.example.dattienbkhn.travel.network.api;

import com.example.dattienbkhn.travel.model.googleinfor.GoogleInfor;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by tiendatbkhn on 20/03/2018.
 */

public interface GoogleInforApi {
    @GET("user/{userid}?alt=json")
    Observable<GoogleInfor> getGoogleInfor(@Path("userid") String userId);
}
