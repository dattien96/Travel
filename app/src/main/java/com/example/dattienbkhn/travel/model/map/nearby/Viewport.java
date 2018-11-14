package com.example.dattienbkhn.travel.model.map.nearby;

import com.example.dattienbkhn.travel.network.message.map.direction.Northeast;
import com.example.dattienbkhn.travel.network.message.map.direction.Southwest;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by dattienbkhn on 14/03/2018.
 */

public class Viewport implements Serializable {
    @SerializedName("northeast")
    @Expose
    private NortheastNearby northeast;
    @SerializedName("southwest")
    @Expose
    private SouthwestNearby southwest;

    public NortheastNearby getNortheast() {
        return northeast;
    }

    public void setNortheast(NortheastNearby northeast) {
        this.northeast = northeast;
    }

    public SouthwestNearby getSouthwest() {
        return southwest;
    }

    public void setSouthwest(SouthwestNearby southwest) {
        this.southwest = southwest;
    }
}
