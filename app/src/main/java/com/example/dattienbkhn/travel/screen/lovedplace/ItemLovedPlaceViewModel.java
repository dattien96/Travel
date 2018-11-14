package com.example.dattienbkhn.travel.screen.lovedplace;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.screen.main.mainfragment.ItemFamousPlaceCountryViewModel;

/**
 * Created by tiendatbkhn on 05/04/2018.
 */

public class ItemLovedPlaceViewModel extends BaseObservable {
    private Place lovedPlace;
    private ItemFamousPlaceCountryViewModel.IItemPlaceClick itemPlaceClick;

    public ItemLovedPlaceViewModel(Place lovedPlace, ItemFamousPlaceCountryViewModel.IItemPlaceClick itemPlaceClick) {
        this.lovedPlace = lovedPlace;
        this.itemPlaceClick = itemPlaceClick;
    }

    public void onItemPlaceClick() {
        itemPlaceClick.onItemPlaceClick(lovedPlace);
    }

    @Bindable
    public Place getLovedPlace() {
        return lovedPlace;
    }

    public void setLovedPlace(Place lovedPlace) {
        this.lovedPlace = lovedPlace;
    }
}
