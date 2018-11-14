package com.example.dattienbkhn.travel.screen.main.favoritefragment;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import com.example.dattienbkhn.travel.databinding.ItemFavoritePlaceBinding;
import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.screen.main.mainfragment.ItemFamousPlaceCountryViewModel;


/**
 * Created by tiendatbkhn on 23/03/2018.
 */

public class ItemFavoriteViewModel extends BaseObservable {
    private Place placeSave;
    private ItemFamousPlaceCountryViewModel.IItemPlaceClick itemPlaceClick;
    private IItemPopupMenu itemPopupMenu;
    private ItemFavoritePlaceBinding binding;

    public ItemFavoriteViewModel(Place placeSave, ItemFamousPlaceCountryViewModel.IItemPlaceClick itemPlaceClick, IItemPopupMenu itemPopupMenu, ItemFavoritePlaceBinding binding) {
        this.placeSave = placeSave;
        this.itemPlaceClick = itemPlaceClick;
        this.itemPopupMenu = itemPopupMenu;
        this.binding = binding;
    }

    public void onPlaceClick() {
        itemPlaceClick.onItemPlaceClick(placeSave);
    }

    public void onShowPopupMenu() {
        itemPopupMenu.onShowPopupMenu(placeSave.getPlaceId(),binding.imgMore);
    }
    @Bindable
    public Place getPlaceSave() {
        return placeSave;
    }

    public void setPlaceSave(Place placeSave) {
        this.placeSave = placeSave;
    }

    public interface IItemPopupMenu {
        void onShowPopupMenu(int placeId,View v);
    }
}
