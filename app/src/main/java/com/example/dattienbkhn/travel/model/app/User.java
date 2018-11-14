package com.example.dattienbkhn.travel.model.app;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by dattienbkhn on 07/02/2018.
 */
public class User implements Serializable {
    @SerializedName("userId")
    @Expose
    private int mUserId;
    @SerializedName("name")
    @Expose
    private String mName;
    @SerializedName("birthDay")
    @Expose
    private String mBirthDay;
    @SerializedName("gender")
    @Expose
    private Integer mGender;
    @SerializedName("avatar")
    @Expose
    private String mAvatar;
    @SerializedName("coverImage")
    @Expose
    private String mCoverImage;
    @SerializedName("address")
    @Expose
    private String mAddress;
    @SerializedName("accId")
    @Expose
    private int mAccId;

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        mUserId = userId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getBirthDay() {
        return mBirthDay;
    }
    public void setBirthDay(String birthDay) {
        mBirthDay = birthDay;
    }

    public Integer getGender() {
        return mGender;
    }

    public void setGender(Integer gender) {
        mGender = gender;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String mAvatar) {
        this.mAvatar = mAvatar;
    }

    public String getCoverImage() {
        return mCoverImage;
    }

    public void setCoverImage(String coverImage) {
        mCoverImage = coverImage;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public int getAccId() {
        return mAccId;
    }

    public void setAccId(int accId) {
        mAccId = accId;
    }

    public String getGenderString() {
        if (mGender != null) {
            if (mGender == 1) {
                return "Male";
            } else return "FeMale";
        }
        return "No information";
    }

    public String getBirthDayString() throws ParseException {
        if (mBirthDay != null) {
            String tmp = mBirthDay.substring(0,mBirthDay.indexOf(" "));
            DateFormat format = new SimpleDateFormat("MM/d/yy", Locale.ENGLISH);
            java.util.Date date = format.parse(tmp);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(date);
        }

        else return "No informarion";
    }

    public String getAddressString() {
        if (mAddress != null)
            return mAddress;
        else return "No information";
    }
}
