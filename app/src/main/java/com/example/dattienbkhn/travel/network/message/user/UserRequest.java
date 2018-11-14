package com.example.dattienbkhn.travel.network.message.user;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by tiendatbkhn on 16/03/2018.
 */

public class UserRequest implements Serializable {
    @Expose
    private String Name;
    @Expose
    private Date BirthDay;
    @Expose
    private Integer Gender;
    @Expose
    private byte[] Avatar;
    @Expose
    private byte[] CoverImage;
    @Expose
    private String Address;
    @Expose
    private int AccId;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Date getBirthDay() {
        return BirthDay;
    }

    public void setBirthDay(Date birthDay) {
        BirthDay = birthDay;
    }

    public Integer getGender() {
        return Gender;
    }

    public void setGender(Integer gender) {
        Gender = gender;
    }

    public byte[] getAvatar() {
        return Avatar;
    }

    public void setAvatar(byte[] avatar) {
        Avatar = avatar;
    }

    public byte[] getCoverImage() {
        return CoverImage;
    }

    public void setCoverImage(byte[] coverImage) {
        CoverImage = coverImage;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getAccId() {
        return AccId;
    }

    public void setAccId(int accId) {
        AccId = accId;
    }
}
