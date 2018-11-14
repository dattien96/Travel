package com.example.dattienbkhn.travel.network.api;

import com.example.dattienbkhn.travel.model.map.nearby.NearByWrapp;
import com.example.dattienbkhn.travel.network.message.map.DirectionResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by dattienbkhn on 06/02/2018.
 */

public interface MapApi {
    //&language=vi
    @GET("api/directions/json?alternatives=true&key=AIzaSyAxmIkmP7GTLXx4JVFWL77kL6tePvcbOwY")
    Observable<DirectionResponse> getDirection(@Query("origin") String origin, @Query("destination") String destination);

    @GET("api/place/nearbysearch/json?key=AIzaSyAxmIkmP7GTLXx4JVFWL77kL6tePvcbOwY")
    Observable<NearByWrapp> getNearbyPlaces(@Query("types") String type, @Query("location") String location, @Query("radius") int radius);

}


