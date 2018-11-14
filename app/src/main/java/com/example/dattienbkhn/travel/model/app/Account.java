package com.example.dattienbkhn.travel.model.app;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by tiendatbkhn on 16/03/2018.
 */

public class Account implements Serializable {
    @SerializedName("accId")
    @Expose
    private int mAccId ;
    @SerializedName("username")
    @Expose
    private String mUsername ;
    @SerializedName("passWd")
    @Expose
    private String mPassWd ;
    @SerializedName("phone")
    @Expose
    private String mPhone;
    @SerializedName("isFaceBook")
    @Expose
    private int mIsFaceBook ;
    @SerializedName("isGoogle")
    @Expose
    private int mIsGoogle ;

    public int getAccId() {
        return mAccId;
    }

    public void setAccId(int accId) {
        mAccId = accId;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getPassWd() {
        return mPassWd;
    }

    public void setPassWd(String passWd) {
        mPassWd = passWd;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public int getIsFaceBook() {
        return mIsFaceBook;
    }

    public void setIsFaceBook(int isFaceBook) {
        mIsFaceBook = isFaceBook;
    }

    public int getIsGoogle() {
        return mIsGoogle;
    }

    public void setIsGoogle(int isGoogle) {
        mIsGoogle = isGoogle;
    }
}
