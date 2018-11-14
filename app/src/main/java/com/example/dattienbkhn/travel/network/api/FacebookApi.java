package com.example.dattienbkhn.travel.network.api;

import com.example.dattienbkhn.travel.model.facebook.fbcover.CoverWrap;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by dattienbkhn on 09/03/2018.
 */

public interface FacebookApi {
    @GET("{user_id}?fields=cover")
    Observable<CoverWrap> getCoverFbImage(@Path("user_id") String userid, @Query("access_token") String token);
}
