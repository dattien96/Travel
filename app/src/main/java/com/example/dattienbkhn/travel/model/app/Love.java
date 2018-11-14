package com.example.dattienbkhn.travel.model.app;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


/**
 * Created by tiendatbkhn on 18/03/2018.
 */

public class Love implements Serializable {
    @SerializedName("accId")
    @Expose
    private int AccId;
    @SerializedName("placeId")
    @Expose
    private int PlaceId;

    public int getAccId() {
        return AccId;
    }

    public void setAccId(int accId) {
        AccId = accId;
    }

    public int getPlaceId() {
        return PlaceId;
    }

    public void setPlaceId(int placeId) {
        PlaceId = placeId;
    }
}
