package com.example.dattienbkhn.travel.screen.main.mainfragment;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;

import com.example.dattienbkhn.travel.model.app.City;

/**
 * Created by dattienbkhn on 08/02/2018.
 */

public class ItemTopCityViewModel extends BaseObservable {
    private City city;
    private IItemCityClick itemCityClick;

    public ItemTopCityViewModel(City city, IItemCityClick itemCityClick) {
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

    public interface IItemCityClick {
        void onItemCityClick(City obj);
    }
}
