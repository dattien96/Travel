package com.example.dattienbkhn.travel.model.weather.currentweather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by dattienbkhn on 07/03/2018.
 */

public class Rain implements Serializable {
    @SerializedName("3h")
    @Expose
    private Double _3h;
    public Double get3h() {
        return _3h;
    }

    public void set3h(Double _3h) {
        this._3h = _3h;
    }
}
