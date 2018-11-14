package com.example.dattienbkhn.travel.network.api;

import com.example.dattienbkhn.travel.model.location.CurrentLocation;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by dattienbkhn on 03/03/2018.
 */

public interface LocationApi {
    @GET("{ip}/json")
    Observable<CurrentLocation> getCurrentLocation(@Path("ip") String ip);
}
