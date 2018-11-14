package com.example.dattienbkhn.travel.model.app;

import java.io.Serializable;

/**
 * Created by dattienbkhn on 11/03/2018.
 */

public class Region implements Serializable {
    private String mRegionName;
    private int mRegionImageRes;

    public Region(String mRegionName, int mRegionImageRes) {
        this.mRegionName = mRegionName;
        this.mRegionImageRes = mRegionImageRes;
    }

    public String getRegionName() {
        return mRegionName;
    }

    public void setRegionName(String mRegionName) {
        this.mRegionName = mRegionName;
    }

    public int getRegionImageRes() {
        return mRegionImageRes;
    }

    public void setRegionImageRes(int mRegionImageRes) {
        this.mRegionImageRes = mRegionImageRes;
    }
}
