package com.example.dattienbkhn.travel.model.app;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by tiendatbkhn on 02/04/2018.
 */

public class Friend implements Serializable {

    @SerializedName("id")
    @Expose
    private int mId;
    @SerializedName("accId")
    @Expose
    private int mAccId;
    @SerializedName("friendId")
    @Expose
    private int mFriendId;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public Integer getAccId() {
        return mAccId;
    }

    public void setAccId(Integer accId) {
        this.mAccId = accId;
    }

    public Integer getFriendId() {
        return mFriendId;
    }

    public void setFriendId(Integer friendId) {
        this.mFriendId = friendId;
    }

}
