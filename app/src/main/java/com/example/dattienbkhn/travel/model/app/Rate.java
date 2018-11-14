package com.example.dattienbkhn.travel.model.app;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by tiendatbkhn on 18/03/2018.
 */

public class Rate implements Serializable {
    @SerializedName("accId")
    @Expose
    private int AccId;
    @SerializedName("placeId")
    @Expose
    private int PlaceId;
    @SerializedName("star")
    @Expose
    private double Star;

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

    public double getStar() {
        return Star;
    }

    public void setStar(double star) {
        Star = star;
    }
}
