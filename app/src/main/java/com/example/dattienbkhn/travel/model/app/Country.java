package com.example.dattienbkhn.travel.model.app;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by dattienbkhn on 06/02/2018.
 */
@Entity
public class Country implements Serializable{
    @PrimaryKey(autoGenerate = true)
    public int mCountryId;

    public int getCountryId() {
        return mCountryId;
    }

    public void setCountryId(int mId) {
        this.mCountryId = mId;
    }
}
