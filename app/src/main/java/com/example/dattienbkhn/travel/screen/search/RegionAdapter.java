package com.example.dattienbkhn.travel.screen.search;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dattienbkhn.travel.R;
import com.example.dattienbkhn.travel.databinding.ItemRegionBinding;
import com.example.dattienbkhn.travel.model.app.Region;
import com.example.dattienbkhn.travel.screen.BaseAdapter;
import com.example.dattienbkhn.travel.screen.BaseItemHolder;

import java.util.List;

/**
 * Created by dattienbkhn on 11/03/2018.
 */

public class RegionAdapter extends RecyclerView.Adapter<RegionAdapter.RegionHolder>
        implements BaseAdapter<List<Region>> {
    private Context ctx;
    private List<Region> regions;
    private ItemRegionViewModel.IItemRegionClick itemRegionClick;
    public RegionAdapter(Context ctx, List<Region> regions) {
        this.ctx = ctx;
        this.regions = regions;
    }

    @Override
    public RegionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemRegionBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(ctx),
                R.layout.item_region,
                parent,
                false
        );
        return new RegionHolder(binding,this.itemRegionClick);
    }

    @Override
    public void onBindViewHolder(RegionHolder holder, int position) {
        holder.setBinding(regions.get(position));
    }

    @Override
    public int getItemCount() {
        return regions.size();
    }

    @Override
    public void setData(List<Region> data) {
        regions.clear();
        regions.addAll(data);
        notifyDataSetChanged();
    }

    public void setItemRegionClick(ItemRegionViewModel.IItemRegionClick itemRegionClick) {
        this.itemRegionClick = itemRegionClick;
    }

    public class RegionHolder extends RecyclerView.ViewHolder implements BaseItemHolder<Region> {
        private ItemRegionBinding binding;
        private ItemRegionViewModel.IItemRegionClick itemRegionClick;
        public RegionHolder(ItemRegionBinding binding, ItemRegionViewModel.IItemRegionClick itemRegionClick) {
            super(binding.getRoot());
            this.binding = binding;
            this.itemRegionClick = itemRegionClick;
        }

        @Override
        public void setBinding(Region obj) {
            binding.setViewModel(new ItemRegionViewModel(obj,itemRegionClick));
            binding.executePendingBindings();

        }
    }
}
