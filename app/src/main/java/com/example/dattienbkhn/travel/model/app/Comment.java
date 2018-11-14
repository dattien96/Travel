package com.example.dattienbkhn.travel.model.app;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by dattienbkhn on 07/02/2018.
 */
@Entity
public class Comment implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @SerializedName("cmtId")
    @Expose
    private int mCmtId;
    @SerializedName("content")
    @Expose
    private String mCmtContent;
    @SerializedName("accId")
    @Expose
    private int mAccId;
    @SerializedName("placeId")
    @Expose
    private int mPlaceId;
    @SerializedName("time")
    @Expose
    private String mCmtTime;

    public String getCmtTime() {
        return mCmtTime;
    }

    public void setCmtTime(String mCmtTime) {
        this.mCmtTime = mCmtTime;
    }

    public String getCmtContent() {
        return mCmtContent;
    }

    public void setCmtContent(String mCmtContent) {
        this.mCmtContent = mCmtContent;
    }

    public int getAccId() {
        return mAccId;
    }

    public void setAccId(int mAccId) {
        this.mAccId = mAccId;
    }

    public int getPlaceId() {
        return mPlaceId;
    }

    public void setPlaceId(int mPlaceId) {
        this.mPlaceId = mPlaceId;
    }

    public int getCmtId() {
        return mCmtId;
    }

    public void setCmtId(int mCmtId) {
        this.mCmtId = mCmtId;
    }

    public String getDateConvertString() {
        return mCmtTime.substring(0,mCmtTime.indexOf(" "));
    }
}
