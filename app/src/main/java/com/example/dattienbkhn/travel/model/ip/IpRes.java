package com.example.dattienbkhn.travel.model.ip;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dattienbkhn on 03/03/2018.
 */

public class IpRes {
    @SerializedName("ip")
    @Expose
    private String ip;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
