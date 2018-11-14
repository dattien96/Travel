package com.example.dattienbkhn.travel.model.app;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by dattienbkhn on 06/02/2018.
 */
@Entity
public class City implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @SerializedName("cityId")
    @Expose
    private int mCityId;

    @SerializedName("name")
    @Expose
    private String mCityName;

    @SerializedName("image")
    @Expose
    private String mCityImageUrl;

    @SerializedName("recommend")
    @Expose
    private String mCityRecommended;

    @SerializedName("care")
    @Expose
    private Integer mCare;

    public int getCityId() {
        return mCityId;
    }

    public void setCityId(int mCityId) {
        this.mCityId = mCityId;
    }

    public String getCityName() {
        return mCityName;
    }

    public void setCityName(String mCityName) {
        this.mCityName = mCityName;
    }

    public String getCityImageUrl() {
        return mCityImageUrl;
    }

    public void setCityImageUrl(String mCityImageUrl) {
        this.mCityImageUrl = mCityImageUrl;
    }

    public String getCityRecommended() {
        return mCityRecommended;
    }

    public void setCityRecommended(String mCityRecommended) {
        this.mCityRecommended = mCityRecommended;
    }

    public Integer getCare() {
        return mCare;
    }

    public void setCare(Integer care) {
        this.mCare = care;
    }


}
