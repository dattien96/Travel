package com.example.dattienbkhn.travel.screen.search;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.dattienbkhn.travel.R;
import com.example.dattienbkhn.travel.databinding.ItemCitySearchBinding;
import com.example.dattienbkhn.travel.databinding.ItemPlaceSearchBinding;
import com.example.dattienbkhn.travel.model.app.City;
import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.screen.BaseAdapter;
import com.example.dattienbkhn.travel.screen.BaseItemHolder;
import com.example.dattienbkhn.travel.screen.main.mainfragment.ItemTopCityViewModel;

import java.util.List;

/**
 * Created by dattienbkhn on 13/03/2018.
 */

public class CitySearchAdapter extends RecyclerView.Adapter<CitySearchAdapter.CitySearchHolder>
        implements BaseAdapter<List<City>> {

    private Context ctx;
    private List<City> cities;
    private ItemTopCityViewModel.IItemCityClick itemCityClick;

    public CitySearchAdapter(Context ctx, List<City> cities) {
        this.ctx = ctx;
        this.cities = cities;
    }

    public void setItemCityClick(ItemTopCityViewModel.IItemCityClick itemCityClick) {
        this.itemCityClick = itemCityClick;
    }

    @Override
    public CitySearchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemCitySearchBinding binding = DataBindingUtil.inflate(LayoutInflater.from(ctx),
                R.layout.item_city_search,
                parent,
                false);

        return new CitySearchHolder(binding,itemCityClick);
    }

    @Override
    public void onBindViewHolder(CitySearchHolder holder, int position) {
        holder.setBinding(cities.get(position));
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    @Override
    public void setData(List<City> data) {
        cities.clear();
        cities.addAll(data);
        notifyDataSetChanged();
    }

    public class CitySearchHolder extends RecyclerView.ViewHolder implements BaseItemHolder<City> {
        private ItemCitySearchBinding binding;
        private ItemTopCityViewModel.IItemCityClick itemCityClick;

        public CitySearchHolder(ItemCitySearchBinding binding, ItemTopCityViewModel.IItemCityClick itemCityClick) {
            super(binding.getRoot());
            this.binding = binding;
            this.itemCityClick = itemCityClick;
        }

        @Override
        public void setBinding(City obj) {
            binding.setViewModel(new ItemCitySearchViewModel(obj, itemCityClick));
            binding.executePendingBindings();
        }
    }
}
