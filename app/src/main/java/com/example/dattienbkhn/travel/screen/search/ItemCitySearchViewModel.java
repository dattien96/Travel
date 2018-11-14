package com.example.dattienbkhn.travel.screen.search;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.dattienbkhn.travel.model.app.City;
import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.screen.main.mainfragment.ItemTopCityViewModel;

/**
 * Created by dattienbkhn on 13/03/2018.
 */

public class ItemCitySearchViewModel extends BaseObservable {
    private City city;
    private ItemTopCityViewModel.IItemCityClick itemCityClick;

    public ItemCitySearchViewModel(City city, ItemTopCityViewModel.IItemCityClick itemCityClick) {
        this.city = city;
        this.itemCityClick = itemCityClick;
    }

    @Bindable
    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public void onCityClick() {
        itemCityClick.onItemCityClick(this.city);

    }
}
