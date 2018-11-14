package com.example.dattienbkhn.travel.model.app;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.example.dattienbkhn.travel.R;
import com.example.dattienbkhn.travel.utils.Constant;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by dattienbkhn on 06/02/2018.
 */
@Entity
public class Place implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @SerializedName("placeId")
    @Expose
    private int mPlaceId;

    @SerializedName("imageUrl")
    @Expose
    private String mPlaceImageUrl;

    @SerializedName("name")
    @Expose
    private String mPlaceName;

    @SerializedName("imgMapUrl")
    @Expose
    private String mPlaceImageMapUrl;

    @SerializedName("review")
    @Expose
    private int mPlaceReview;

    @SerializedName("rate")
    @Expose
    private float mPlaceRate;

    @SerializedName("addressId")
    @Expose
    private int mAddressId;

    @SerializedName("cityId")
    @Expose
    private int mCityId;

    @SerializedName("typeGenerate")
    @Expose
    private String mTypeGenerate;

    @SerializedName("typeDetails")
    @Expose
    private String mTypeDetails;

    @SerializedName("groupType")
    @Expose
    private int mGroupType;

    @SerializedName("latitude")
    @Expose
    private double mLatitude;
    @SerializedName("longitude")
    @Expose
    private double mLongitude;


    private int mMapPin;

    private int mMapPinActive;

    public int getMapPin() {

        if (this.mTypeGenerate.equalsIgnoreCase(Constant.SHARED_PLACE_SEE_TYPE)) {
            this.mMapPin = R.drawable.map_pin_outdoors;

        } else if (this.mTypeGenerate.equalsIgnoreCase(Constant.SHARED_PLACE_FOODIES_TYPE)) {
            this.mMapPin = R.drawable.map_pin_eat_default;

        } else if (this.mTypeGenerate.equalsIgnoreCase(Constant.SHARED_PLACE_DRINK_TYPE)) {
            this.mMapPin = (R.drawable.map_pin_bar);

        } else if (this.mTypeGenerate.equalsIgnoreCase(Constant.SHARED_PLACE_STAY_TYPE)) {
            this.mMapPin = R.drawable.map_pin_hostel;
        }

        return mMapPin;
    }

    public void setMapPin(int mMapPin) {
        this.mMapPin = mMapPin;
    }

    public int getMapPinActive() {

        if (this.mTypeGenerate.equalsIgnoreCase(Constant.SHARED_PLACE_SEE_TYPE)) {
            this.mMapPinActive = (R.drawable.map_pin_outdoors_active);
        } else if (this.mTypeGenerate.equalsIgnoreCase(Constant.SHARED_PLACE_FOODIES_TYPE)) {
            this.mMapPinActive = (R.drawable.map_pin_eat_default_active);
        } else if (this.mTypeGenerate.equalsIgnoreCase(Constant.SHARED_PLACE_DRINK_TYPE)) {
            this.mMapPinActive = (R.drawable.map_pin_bar_active);
        } else if (this.mTypeGenerate.equalsIgnoreCase(Constant.SHARED_PLACE_STAY_TYPE)) {
            this.mMapPinActive = (R.drawable.map_pin_hostel_active);
        }

        return mMapPinActive;
    }

    public void setMapPinActive(int mMapPinActive) {
        this.mMapPinActive = mMapPinActive;
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

    public int getGroupType() {
        return mGroupType;
    }

    public void setGroupType(int mGroupType) {
        this.mGroupType = mGroupType;
    }

    public int getPlaceId() {
        return mPlaceId;
    }

    public void setPlaceId(int mPlaceId) {
        this.mPlaceId = mPlaceId;
    }

    public String getPlaceImageUrl() {
        return mPlaceImageUrl;
    }

    public void setPlaceImageUrl(String mPlaceImageUrl) {
        this.mPlaceImageUrl = mPlaceImageUrl;
    }

    public String getPlaceName() {
        return mPlaceName;
    }

    public void setPlaceName(String mPlaceName) {
        this.mPlaceName = mPlaceName;
    }

    public String getPlaceImageMapUrl() {
        return mPlaceImageMapUrl;
    }

    public void setPlaceImageMapUrl(String mPlaceImageMapUrl) {
        this.mPlaceImageMapUrl = mPlaceImageMapUrl;
    }

    public int getPlaceReview() {
        return mPlaceReview;
    }

    public void setPlaceReview(int mPlaceReview) {
        this.mPlaceReview = mPlaceReview;
    }

    public float getPlaceRate() {
        return mPlaceRate;
    }

    public void setPlaceRate(float mPlaceRate) {
        this.mPlaceRate = mPlaceRate;
    }

    public int getAddressId() {
        return mAddressId;
    }

    public void setAddressId(int mAddressId) {
        this.mAddressId = mAddressId;
    }

    public int getCityId() {
        return mCityId;
    }

    public void setCityId(int mCityId) {
        this.mCityId = mCityId;
    }

    public String getTypeGenerate() {
        return mTypeGenerate;
    }

    public void setTypeGenerate(String mTypeGenerate) {
        this.mTypeGenerate = mTypeGenerate;
    }

    public String getTypeDetails() {
        return mTypeDetails;
    }

    public void setTypeDetails(String mTypeDetails) {
        this.mTypeDetails = mTypeDetails;
    }

    public String stringViewOfPlace() {

        return this.mPlaceReview + " views";
    }

    public int getPlaceIcon() {
        int res = 0;
        if (this.mTypeGenerate.equalsIgnoreCase(Constant.SHARED_PLACE_SEE_TYPE)) {
            res = (R.drawable.default_attractions);
        } else if (this.mTypeGenerate.equalsIgnoreCase(Constant.SHARED_PLACE_FOODIES_TYPE)) {
            res = (R.drawable.default_restaurants);
        } else if (this.mTypeGenerate.equalsIgnoreCase(Constant.SHARED_PLACE_DRINK_TYPE)) {
            res = (R.drawable.default_nightlife);
        } else if (this.mTypeGenerate.equalsIgnoreCase(Constant.SHARED_PLACE_STAY_TYPE)) {
            res = (R.drawable.default_hotels);
        }

        return res;
    }
}
