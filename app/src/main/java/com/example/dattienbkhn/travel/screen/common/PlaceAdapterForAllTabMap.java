package com.example.dattienbkhn.travel.screen.common;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.dattienbkhn.travel.R;
import com.example.dattienbkhn.travel.databinding.ItemPlaceForMapBinding;
import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.screen.BaseAdapter;
import com.example.dattienbkhn.travel.screen.BaseItemHolder;
import com.example.dattienbkhn.travel.screen.main.mainfragment.ItemFamousPlaceCountryViewModel;

import java.util.List;

/**
 * Created by dattienbkhn on 10/02/2018.
 */

public class PlaceAdapterForAllTabMap extends RecyclerView.Adapter<PlaceAdapterForAllTabMap.MapPlaceViewHolder>
        implements BaseAdapter<List<Place>> {
    private Context ctx;
    private List<Place> places;
    private ItemFamousPlaceCountryViewModel.IItemPlaceClick itemPlaceClick;
    private IAdapterCallBack adapterCallBack;

    public PlaceAdapterForAllTabMap(Context ctx, List<Place> places) {
        this.ctx = ctx;
        this.places = places;

    }



    @Override
    public MapPlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemPlaceForMapBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(ctx),
                R.layout.item_place_for_map,
                parent,
                false
        );

        return new MapPlaceViewHolder(binding, itemPlaceClick);
    }

    public void setAdapterCallBack(IAdapterCallBack adapterCallBack) {
        this.adapterCallBack = adapterCallBack;
    }

    /**
     *
     * @param : pos of item need tobe drawed
     *
     */


    @Override
    public void onBindViewHolder(MapPlaceViewHolder holder, int position) {
        holder.setBinding(places.get(position));

    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public void setItemPlaceClick(ItemFamousPlaceCountryViewModel.IItemPlaceClick iItemPlaceClick) {
        this.itemPlaceClick = iItemPlaceClick;
    }


    @Override
    public void setData(List<Place> data) {
        places.clear();
        places.addAll(data);
        notifyDataSetChanged();
    }

    public interface IAdapterCallBack {
        void onDrawComplete();

    }

    public class MapPlaceViewHolder extends RecyclerView.ViewHolder implements BaseItemHolder<Place> {
        private ItemPlaceForMapBinding binding;
        private ItemFamousPlaceCountryViewModel.IItemPlaceClick itemPlaceClick;

        public MapPlaceViewHolder(ItemPlaceForMapBinding binding, ItemFamousPlaceCountryViewModel.IItemPlaceClick itemPlaceClick) {
            super(binding.getRoot());
            this.binding = binding;
            this.itemPlaceClick = itemPlaceClick;
        }

        @Override
        public void setBinding(Place obj) {
            binding.setViewModel(new ItemPlaceMapViewModel(obj, itemPlaceClick));
            binding.executePendingBindings();
        }


    }
}
