package com.example.dattienbkhn.travel.network.message.account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by tiendatbkhn on 20/03/2018.
 */

public class AccResponse implements Serializable {
    @SerializedName("username")
    @Expose
    private String Username;
    @SerializedName("phone")
    @Expose
    private String Phone;
    @SerializedName("isFaceBook")
    @Expose
    private int IsFaceBook;
    @SerializedName("isGoogle")
    @Expose
    private int IsGoogle;

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
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
}
