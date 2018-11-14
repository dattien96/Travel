package com.example.dattienbkhn.travel.screen.search;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;

import com.example.dattienbkhn.travel.model.app.City;
import com.example.dattienbkhn.travel.model.app.Region;

/**
 * Created by dattienbkhn on 11/03/2018.
 */

public class ItemRegionViewModel extends BaseObservable {
    private Region region;
    private IItemRegionClick itemRegionClick;
    public final ObservableField<Boolean> isSelected = new ObservableField<>();

    public ItemRegionViewModel(Region region,IItemRegionClick itemRegionClick) {
        this.region = region;
        this.itemRegionClick = itemRegionClick;
        isSelected.set(false);
    }

    public void onRegionClick() {
        if (isSelected.get()) {
            isSelected.set(false);
            itemRegionClick.onItemRegionClick(region,false);
        } else {
            isSelected.set(true);
            itemRegionClick.onItemRegionClick(region,true);
        }

    }

    @Bindable
    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public interface IItemRegionClick {
        void onItemRegionClick(Region obj,boolean b);
    }
}
