package com.example.dattienbkhn.travel.screen.common;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.dattienbkhn.travel.model.app.Image;

/**
 * Created by dattienbkhn on 10/02/2018.
 */

public class ItemImageSLideViewModel extends BaseObservable {
    private Image image;

    public ItemImageSLideViewModel(Image image) {
        this.image = image;
    }

    @Bindable
    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
