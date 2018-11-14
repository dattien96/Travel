package com.example.dattienbkhn.travel.screen.main.mainfragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.dattienbkhn.travel.R;
import com.example.dattienbkhn.travel.databinding.ItemPlaceCountryFamousBinding;
import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.screen.BaseAdapter;
import com.example.dattienbkhn.travel.screen.BaseItemHolder;

import java.util.List;

/**
 * Created by dattienbkhn on 08/02/2018.
 */

public class PlaceofCountryAdapter extends RecyclerView.Adapter<PlaceofCountryAdapter.PlaceHolder>
        implements BaseAdapter<List<Place>> {
    private Context ctx;
    private List<Place> listPlace;
    private ItemFamousPlaceCountryViewModel.IItemPlaceClick itemPlaceClick;

    public PlaceofCountryAdapter(Context ctx, List<Place> listPlace) {
        this.ctx = ctx;
        this.listPlace = listPlace;
    }

    public void setItemPlaceClick(ItemFamousPlaceCountryViewModel.IItemPlaceClick itemPlaceClick) {
        this.itemPlaceClick = itemPlaceClick;
    }


    @Override
    public PlaceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemPlaceCountryFamousBinding binding = DataBindingUtil.inflate(LayoutInflater.from(ctx),
                R.layout.item_place_country_famous,
                parent,
                false);

        return new PlaceHolder(itemPlaceClick, binding);
    }

    @Override
    public void onBindViewHolder(PlaceHolder holder, int position) {
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

    public class PlaceHolder extends RecyclerView.ViewHolder implements BaseItemHolder<Place> {
        private ItemFamousPlaceCountryViewModel.IItemPlaceClick itemPlaceClick;
        private ItemPlaceCountryFamousBinding binding;

        public PlaceHolder(ItemFamousPlaceCountryViewModel.IItemPlaceClick itemPlaceClick, ItemPlaceCountryFamousBinding binding) {
            super(binding.getRoot()
            );
            this.itemPlaceClick = itemPlaceClick;
            this.binding = binding;
        }

        @Override
        public void setBinding(Place obj) {
            binding.setViewModel(new ItemFamousPlaceCountryViewModel(itemPlaceClick, obj));
            binding.executePendingBindings();
        }
    }
}
