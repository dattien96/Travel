package com.example.dattienbkhn.travel.network.api;

import com.example.dattienbkhn.travel.model.ip.IpRes;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by dattienbkhn on 03/03/2018.
 */

public interface IpApi {
    @GET(" ")
    Observable<IpRes> getCurrentIpAddress();

}
