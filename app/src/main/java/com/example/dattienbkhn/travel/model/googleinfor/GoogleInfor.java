
package com.example.dattienbkhn.travel.model.googleinfor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GoogleInfor {

    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("encoding")
    @Expose
    private String encoding;
    @SerializedName("entry")
    @Expose
    private Entry entry;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public Entry getEntry() {
        return entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }

}
