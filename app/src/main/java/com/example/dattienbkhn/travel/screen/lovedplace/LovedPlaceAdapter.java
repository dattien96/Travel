package com.example.dattienbkhn.travel.screen.lovedplace;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.dattienbkhn.travel.R;
import com.example.dattienbkhn.travel.databinding.ItemLovedPlaceBinding;
import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.screen.BaseAdapter;
import com.example.dattienbkhn.travel.screen.BaseItemHolder;
import com.example.dattienbkhn.travel.screen.main.mainfragment.ItemFamousPlaceCountryViewModel;

import java.util.List;

/**
 * Created by tiendatbkhn on 05/04/2018.
 */

public class LovedPlaceAdapter extends RecyclerView.Adapter<LovedPlaceAdapter.LovedPlaceHolder>
        implements BaseAdapter<List<Place>> {
    private Context ctx;
    private List<Place> places;
    private ItemFamousPlaceCountryViewModel.IItemPlaceClick itemPlaceClick;

    public LovedPlaceAdapter(Context ctx, List<Place> places, ItemFamousPlaceCountryViewModel.IItemPlaceClick itemPlaceClick) {
        this.ctx = ctx;
        this.places = places;
        this.itemPlaceClick = itemPlaceClick;
    }

    @NonNull
    @Override
    public LovedPlaceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLovedPlaceBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(ctx),
                R.layout.item_loved_place,
                parent,
                false
        );
        return new LovedPlaceHolder(binding, itemPlaceClick);
    }

    @Override
    public void onBindViewHolder(@NonNull LovedPlaceHolder holder, int position) {
        holder.setBinding(places.get(position));
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    @Override
    public void setData(List<Place> data) {
        places.clear();
        places.addAll(data);
        notifyDataSetChanged();
    }

    public class LovedPlaceHolder extends RecyclerView.ViewHolder implements BaseItemHolder<Place> {
        private ItemLovedPlaceBinding binding;
        private ItemFamousPlaceCountryViewModel.IItemPlaceClick itemPlaceClick;

        public LovedPlaceHolder(ItemLovedPlaceBinding binding, ItemFamousPlaceCountryViewModel.IItemPlaceClick itemPlaceClick) {
            super(binding.getRoot());
            this.binding = binding;
            this.itemPlaceClick = itemPlaceClick;
        }

        @Override
        public void setBinding(Place obj) {
            binding.setViewModel(new ItemLovedPlaceViewModel(obj, itemPlaceClick));
            binding.executePendingBindings();
        }
    }

}
