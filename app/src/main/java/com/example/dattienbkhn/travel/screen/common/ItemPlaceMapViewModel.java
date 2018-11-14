package com.example.dattienbkhn.travel.screen.common;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.dattienbkhn.travel.BR;
import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.screen.main.mainfragment.ItemFamousPlaceCountryViewModel;

/**
 * Created by dattienbkhn on 10/02/2018.
 */

public class ItemPlaceMapViewModel extends BaseObservable {
    private Place place;
    private ItemFamousPlaceCountryViewModel.IItemPlaceClick itemPlaceClick;


    public ItemPlaceMapViewModel(Place place, ItemFamousPlaceCountryViewModel.IItemPlaceClick itemPlaceClick) {
        this.place = place;
        this.itemPlaceClick = itemPlaceClick;

    }

    public void onPlaceItemClick() {
        itemPlaceClick.onItemPlaceClick(this.place);
    }



    @Bindable
    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
        notifyPropertyChanged(BR.place);
    }
}
