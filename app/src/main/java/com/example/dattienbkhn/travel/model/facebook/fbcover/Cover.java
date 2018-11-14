package com.example.dattienbkhn.travel.model.facebook.fbcover;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dattienbkhn on 09/03/2018.
 */

public class Cover {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("offset_x")
    @Expose
    private Integer offsetX;
    @SerializedName("offset_y")
    @Expose
    private Integer offsetY;
    @SerializedName("source")
    @Expose
    private String source;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(Integer offsetX) {
        this.offsetX = offsetX;
    }

    public Integer getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(Integer offsetY) {
        this.offsetY = offsetY;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
