package com.example.dattienbkhn.travel.screen.main.mainfragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.dattienbkhn.travel.R;
import com.example.dattienbkhn.travel.databinding.ItemCityBinding;
import com.example.dattienbkhn.travel.model.app.City;
import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.screen.BaseAdapter;
import com.example.dattienbkhn.travel.screen.BaseItemHolder;

import java.util.List;

/**
 * Created by dattienbkhn on 08/02/2018.
 */

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityHolder>
        implements BaseAdapter<List<City>> {
    private Context ctx;
    private List<City> cityList;
    private ItemTopCityViewModel.IItemCityClick itemCityClick;

    public CityAdapter(Context ctx, List<City> cityList) {
        this.ctx = ctx;
        this.cityList = cityList;
    }

    public void setItemCityClick(ItemTopCityViewModel.IItemCityClick itemCityClick) {
        this.itemCityClick = itemCityClick;
    }



    @Override
    public CityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemCityBinding binding = DataBindingUtil.inflate(LayoutInflater.from(ctx),
                R.layout.item_city,
                parent, false);

        return new CityHolder(itemCityClick, binding);
    }

    @Override
    public void onBindViewHolder(CityHolder holder, int position) {
        holder.setBinding(cityList.get(position));
    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    @Override
    public void setData(List<City> data) {
        cityList.clear();
        cityList.addAll(data);
        notifyDataSetChanged();
    }

    public class CityHolder extends RecyclerView.ViewHolder implements BaseItemHolder<City> {
        ItemTopCityViewModel.IItemCityClick itemCityClick;
        ItemCityBinding binding;

        public CityHolder(ItemTopCityViewModel.IItemCityClick itemCityClick, ItemCityBinding binding) {
            super(binding.getRoot());
            this.itemCityClick = itemCityClick;
            this.binding = binding;
        }

        @Override
        public void setBinding(City obj) {
            binding.setViewModel(new ItemTopCityViewModel(obj, itemCityClick));

        }


    }
}
