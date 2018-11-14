package com.example.dattienbkhn.travel.model.app;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by dattienbkhn on 10/02/2018.
 *
 */


public class Image implements Serializable {

    @SerializedName("imageUrl")
    @Expose
    private String mImageUrl;

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }


}
