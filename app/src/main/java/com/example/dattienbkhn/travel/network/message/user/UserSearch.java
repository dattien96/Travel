package com.example.dattienbkhn.travel.network.message.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tiendatbkhn on 25/04/2018.
 */

public class UserSearch {
    @SerializedName("name")
    @Expose
    private String Name;
    @SerializedName("avatar")
    @Expose
    private String Avatar;
    @SerializedName("accId")
    @Expose
    private int AccId;
    @SerializedName("isFaceBook")
    @Expose
    private int IsFaceBook;
    @SerializedName("isGoogle")
    @Expose
    private int IsGoogle;
    @SerializedName("isFollowed")
    @Expose
    private int IsFollowed;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public int getAccId() {
        return AccId;
    }

    public void setAccId(int accId) {
        AccId = accId;
    }

    public int getIsFaceBook() {
        return IsFaceBook;
    }

    public void setIsFaceBook(int isFaceBook) {
        IsFaceBook = isFaceBook;
    }

    public int getIsGoogle() {
        return IsGoogle;
    }

    public void setIsGoogle(int isGoogle) {
        IsGoogle = isGoogle;
    }

    public int getIsFollowed() {
        return IsFollowed;
    }

    public void setIsFollowed(int isFollowed) {
        IsFollowed = isFollowed;
    }
}
