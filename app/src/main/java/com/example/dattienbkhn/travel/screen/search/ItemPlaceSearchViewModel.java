package com.example.dattienbkhn.travel.screen.search;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.screen.main.mainfragment.ItemFamousPlaceCountryViewModel;

/**
 * Created by dattienbkhn on 12/03/2018.
 */

public class ItemPlaceSearchViewModel extends BaseObservable {
    private Place place;
    private ItemFamousPlaceCountryViewModel.IItemPlaceClick itemPlaceClick;

    public ItemPlaceSearchViewModel(Place place,ItemFamousPlaceCountryViewModel.IItemPlaceClick itemPlaceClick) {
        this.place = place;
        this.itemPlaceClick = itemPlaceClick;
    }

    public void onPlaceClick(){
        itemPlaceClick.onItemPlaceClick(this.place);
    }

    @Bindable
    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }
}
