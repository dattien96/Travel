package com.example.dattienbkhn.travel.screen.detailsmap;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dattienbkhn.travel.R;
import com.example.dattienbkhn.travel.databinding.ItemMapDirectionBinding;
import com.example.dattienbkhn.travel.databinding.ItemPlaceFamousCityBinding;
import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.network.message.map.direction.Step;
import com.example.dattienbkhn.travel.screen.BaseAdapter;
import com.example.dattienbkhn.travel.screen.BaseItemHolder;
import com.example.dattienbkhn.travel.screen.city.homeFragment.PlaceAdapter;

import java.util.List;

/**
 * Created by dattienbkhn on 05/03/2018.
 */

public class DirectionAdapter extends RecyclerView.Adapter<DirectionAdapter.DirectionHolder>
        implements BaseAdapter<List<Step>> {
    private Context ctx;
    private List<Step> steps;

    public DirectionAdapter(Context ctx, List<Step> steps) {
        this.ctx = ctx;
        this.steps = steps;
    }

    @Override
    public DirectionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMapDirectionBinding binding = DataBindingUtil.inflate(LayoutInflater.from(ctx),
                R.layout.item_map_direction,
                parent, false);

        return new DirectionHolder(binding);
    }

    @Override
    public void onBindViewHolder(DirectionHolder holder, int position) {
        holder.setBinding(steps.get(position));
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    @Override
    public void setData(List<Step> data) {
        steps.clear();
        steps.addAll(data);
        notifyDataSetChanged();

    }

    public class DirectionHolder extends RecyclerView.ViewHolder implements BaseItemHolder<Step> {
        private ItemMapDirectionBinding binding;

        public DirectionHolder(ItemMapDirectionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void setBinding(Step obj) {
            binding.setViewModel(new ItemDirectionViewModel(obj));
            binding.executePendingBindings();
        }
    }
}
