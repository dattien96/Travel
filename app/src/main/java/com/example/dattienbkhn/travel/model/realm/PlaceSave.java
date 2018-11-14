package com.example.dattienbkhn.travel.model.realm;

import io.realm.RealmObject;

/**
 * Created by tiendatbkhn on 26/04/2018.
 */

public class PlaceSave extends RealmObject {
    private int id;
    private String name;
    private String type;
    private String image;
    private int view;
    private float rate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
