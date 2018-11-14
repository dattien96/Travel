package com.example.dattienbkhn.travel.screen.city.homeFragment;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.screen.main.mainfragment.ItemFamousPlaceCountryViewModel;

/**
 * Created by dattienbkhn on 09/02/2018.
 */

public class ItemFamousPlaceCityViewHolder extends BaseObservable {
    private Place place;
    private ItemFamousPlaceCountryViewModel.IItemPlaceClick iItemPlaceClick;

    public ItemFamousPlaceCityViewHolder(Place place, ItemFamousPlaceCountryViewModel.IItemPlaceClick iItemPlaceClick) {
        this.place = place;
        this.iItemPlaceClick = iItemPlaceClick;
    }

    public void onPlaceItemClick() {
        iItemPlaceClick.onItemPlaceClick(this.place);
    }

    @Bindable
    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }
}
