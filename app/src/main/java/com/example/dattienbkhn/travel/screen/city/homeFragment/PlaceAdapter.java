package com.example.dattienbkhn.travel.screen.city.homeFragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.dattienbkhn.travel.R;
import com.example.dattienbkhn.travel.databinding.ItemPlaceFamousCityBinding;
import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.screen.BaseAdapter;
import com.example.dattienbkhn.travel.screen.BaseItemHolder;
import com.example.dattienbkhn.travel.screen.main.mainfragment.ItemFamousPlaceCountryViewModel;

import java.util.List;

/**
 * Created by dattienbkhn on 09/02/2018.
 */

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceHolder>
        implements BaseAdapter<List<Place>> {
    private ItemFamousPlaceCountryViewModel.IItemPlaceClick iItemPlaceClick;
    private Context ctx;
    private List<Place> places;


    public PlaceAdapter(Context ctx, List<Place> places) {
        this.ctx = ctx;
        this.places = places;
    }

    @Override
    public PlaceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemPlaceFamousCityBinding binding = DataBindingUtil.inflate(LayoutInflater.from(ctx),
                R.layout.item_place_famous_city,
                parent, false);

        return new PlaceHolder(iItemPlaceClick, binding);

    }

    @Override
    public void onBindViewHolder(PlaceHolder holder, int position) {
        holder.setBinding(places.get(position));
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public void setItemPlaceClick(ItemFamousPlaceCountryViewModel.IItemPlaceClick iItemPlaceClick) {
        this.iItemPlaceClick = iItemPlaceClick;
    }

    @Override
    public void setData(List<Place> data) {
        places.clear();
        places.addAll(data);
        notifyDataSetChanged();
    }


    public class PlaceHolder extends RecyclerView.ViewHolder implements BaseItemHolder<Place> {
        private ItemFamousPlaceCountryViewModel.IItemPlaceClick iItemPlaceClick;
        private ItemPlaceFamousCityBinding binding;

        public PlaceHolder(ItemFamousPlaceCountryViewModel.IItemPlaceClick iItemPlaceClick, ItemPlaceFamousCityBinding binding) {
            super(binding.getRoot());
            this.iItemPlaceClick = iItemPlaceClick;
            this.binding = binding;
        }

        @Override
        public void setBinding(Place obj) {
            binding.setViewModel(new ItemFamousPlaceCityViewHolder(obj, iItemPlaceClick));
            binding.executePendingBindings();
        }


    }
}
