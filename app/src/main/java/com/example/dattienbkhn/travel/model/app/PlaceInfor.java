package com.example.dattienbkhn.travel.model.app;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by dattienbkhn on 12/02/2018.
 */
@Entity
public class PlaceInfor extends BaseObservable implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @SerializedName("addressId")
    @Expose
    private Integer mAddressId;
    @SerializedName("name")
    @Expose
    private String mName;
    @SerializedName("description")
    @Expose
    private String mDescription;
    @SerializedName("latitude")
    @Expose
    private double mLatitude;
    @SerializedName("longitude")
    @Expose
    private double mLongitude;
    @SerializedName("openHour")
    @Expose
    private String mOpenHour;
    @SerializedName("phone")
    @Expose
    private String mPhone;
    @SerializedName("attractionType")
    @Expose
    private String mAttractionType;
    @SerializedName("cuisine")
    @Expose
    private String mCuisine;
    @SerializedName("hotelClass")
    @Expose
    private String mHotelClass;
    @SerializedName("hotelCost")
    @Expose
    private String mHotelCost;

    public Integer getAddressId() {
        return mAddressId;
    }

    public void setAddressId(Integer mAddressId) {
        this.mAddressId = mAddressId;
    }

    @Bindable
    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double mLongitude) {
        this.mLongitude = mLongitude;
    }

    public String getOpenHour() {
        return mOpenHour;
    }

    public void setOpenHour(String mOpenHour) {
        this.mOpenHour = mOpenHour;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        this.mPhone = phone;
    }

    public String getAttractionType() {
        return mAttractionType;
    }

    public void setAttractionType(String attractionType) {
        this.mAttractionType = attractionType;
    }

    public String getCuisine() {
        return mCuisine;
    }

    public void setCuisine(String cuisine) {
        this.mCuisine = cuisine;
    }

    public String getHotelClass() {
        return mHotelClass;
    }

    public void setHotelClass(String hotelClass) {
        this.mHotelClass = hotelClass;
    }

    public String getHotelCost() {
        return mHotelCost;
    }

    public void setHotelCost(String hotelCost) {
        this.mHotelCost = hotelCost;
    }
}
