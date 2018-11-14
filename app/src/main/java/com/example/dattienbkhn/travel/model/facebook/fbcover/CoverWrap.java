package com.example.dattienbkhn.travel.model.facebook.fbcover;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dattienbkhn on 09/03/2018.
 */

public class CoverWrap {
    @SerializedName("cover")
    @Expose
    private Cover cover;
    @SerializedName("id")
    @Expose
    private String id;

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
