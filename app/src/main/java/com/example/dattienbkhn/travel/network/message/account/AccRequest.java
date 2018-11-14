package com.example.dattienbkhn.travel.network.message.account;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by tiendatbkhn on 16/03/2018.
 */

public class AccRequest implements Serializable {

    @Expose
    private String Username;
    @Expose
    private String PassWd;
    @Expose
    private String Phone;
    @Expose
    private int IsFaceBook;
    @Expose
    private int IsGoogle;

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassWd() {
        return PassWd;
    }

    public void setPassWd(String passWd) {
        PassWd = passWd;
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
