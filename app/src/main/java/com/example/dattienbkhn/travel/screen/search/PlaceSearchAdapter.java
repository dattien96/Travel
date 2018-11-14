package com.example.dattienbkhn.travel.screen.search;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.dattienbkhn.travel.R;
import com.example.dattienbkhn.travel.databinding.ItemPlaceCountryFamousBinding;
import com.example.dattienbkhn.travel.databinding.ItemPlaceSearchBinding;
import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.screen.BaseAdapter;
import com.example.dattienbkhn.travel.screen.BaseItemHolder;
import com.example.dattienbkhn.travel.screen.main.mainfragment.ItemFamousPlaceCountryViewModel;
import com.example.dattienbkhn.travel.screen.main.mainfragment.PlaceofCountryAdapter;

import java.util.List;

/**
 * Created by dattienbkhn on 12/03/2018.
 */

public class PlaceSearchAdapter extends RecyclerView.Adapter<PlaceSearchAdapter.PlaceSearchHolder>
        implements BaseAdapter<List<Place>> {

    private Context ctx;
    private List<Place> listPlace;
    private ItemFamousPlaceCountryViewModel.IItemPlaceClick itemPlaceClick;

    public PlaceSearchAdapter(Context ctx, List<Place> listPlace) {
        this.ctx = ctx;
        this.listPlace = listPlace;
    }

    public void setItemPlaceClick(ItemFamousPlaceCountryViewModel.IItemPlaceClick itemPlaceClick) {
        this.itemPlaceClick = itemPlaceClick;
    }

    @Override
    public PlaceSearchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemPlaceSearchBinding binding = DataBindingUtil.inflate(LayoutInflater.from(ctx),
                R.layout.item_place_search,
                parent,
                false);

        return new PlaceSearchHolder(binding,itemPlaceClick);
    }

    @Override
    public void onBindViewHolder(PlaceSearchHolder holder, int position) {
        holder.setBinding(listPlace.get(position));
    }

    @Override
    public int getItemCount() {
        return listPlace.size();
    }

    @Override
    public void setData(List<Place> data) {
        listPlace.clear();
        listPlace.addAll(data);
        notifyDataSetChanged();
    }

    public class PlaceSearchHolder extends RecyclerView.ViewHolder implements BaseItemHolder<Place> {
        private ItemPlaceSearchBinding binding;
        private ItemFamousPlaceCountryViewModel.IItemPlaceClick itemPlaceClick;

        public PlaceSearchHolder(ItemPlaceSearchBinding binding,ItemFamousPlaceCountryViewModel.IItemPlaceClick itemPlaceClick) {
            super(binding.getRoot()
            );
            this.binding = binding;
            this.itemPlaceClick = itemPlaceClick;
        }

        @Override
        public void setBinding(Place obj) {
            binding.setViewModel(new ItemPlaceSearchViewModel(obj,itemPlaceClick));
            binding.executePendingBindings();
        }
    }
}
