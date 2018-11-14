package com.example.dattienbkhn.travel.network.message.place;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dattienbkhn on 25/02/2018.
 */

public class PlaceCoordinate {
    @SerializedName("placeId")
    @Expose
    private int mPlaceId;
    @SerializedName("addressId")
    @Expose
    private int mAddressId;
    @SerializedName("placeName")
    @Expose
    private String mPlaceName;
    @SerializedName("placeType")
    @Expose
    private int mPlaceType;
    @SerializedName("latitude")
    @Expose
    private double mLatitude;
    @SerializedName("longitude")
    @Expose
    private double mLongitude;

    public String getPlaceName() {
        return mPlaceName;
    }

    public int getPlaceType() {
        return mPlaceType;
    }

    public void setPlaceType(int mPlaceType) {
        this.mPlaceType = mPlaceType;
    }

    public void setPlaceName(String placeName) {
        this.mPlaceName = placeName;
    }

    public int getPlaceId() {
        return mPlaceId;
    }

    public void setPlaceId(Integer placeId) {
        this.mPlaceId = placeId;
    }

    public int getAddressId() {
        return mAddressId;
    }

    public void setAddressId(Integer addressId) {
        this.mAddressId = addressId;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(Double latitude) {
        this.mLatitude = latitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(Double longitude) {
        this.mLongitude = longitude;
    }

}
