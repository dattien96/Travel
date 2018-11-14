package com.example.dattienbkhn.travel.screen.main.mainfragment;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;

import com.example.dattienbkhn.travel.model.app.Place;

/**
 * Created by dattienbkhn on 08/02/2018.
 */

public class ItemFamousPlaceCountryViewModel extends BaseObservable {
    private IItemPlaceClick listener;
    public final ObservableField<Place>  place = new ObservableField<>();

    public ItemFamousPlaceCountryViewModel(IItemPlaceClick listener, Place place) {
        this.listener = listener;
        this.place.set(place);
    }

    public void onPlaceClick(){
        listener.onItemPlaceClick(this.place.get());
    }


    public interface IItemPlaceClick {
        void onItemPlaceClick(Place obj);
    }
}
