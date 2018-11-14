
package com.example.dattienbkhn.travel.model.googleinfor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GphotoNickname {

    @SerializedName("$t")
    @Expose
    private String t;

    public String gett() {
        return t;
    }

    public void sett(String t) {
        this.t = t;
    }

}
