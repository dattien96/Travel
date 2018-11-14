package com.example.dattienbkhn.travel.model.googleinfor;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Title {

    @SerializedName("$t")
    @Expose
    private String t;
    @SerializedName("type")
    @Expose
    private String type;

    public String gett() {
        return t;
    }

    public void sett(String t) {
        this.t = t;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
