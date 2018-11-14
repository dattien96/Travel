package com.example.dattienbkhn.travel.model.map.nearby;

import android.location.Location;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by dattienbkhn on 14/03/2018.
 */

public class GeometryNearby implements Serializable {
    @SerializedName("location")
    @Expose
    private LocationNearby location;
    @SerializedName("viewport")
    @Expose
    private Viewport viewport;

    public LocationNearby getLocation() {
        return location;
    }

    public void setLocation(LocationNearby location) {
        this.location = location;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }
}
