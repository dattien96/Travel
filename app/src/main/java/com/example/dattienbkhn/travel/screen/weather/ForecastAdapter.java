package com.example.dattienbkhn.travel.screen.weather;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dattienbkhn.travel.R;
import com.example.dattienbkhn.travel.databinding.ItemForecastBinding;
import com.example.dattienbkhn.travel.model.weather.forecast.ForecastObj;
import com.example.dattienbkhn.travel.model.weather.forecast.ForecastWrap;
import com.example.dattienbkhn.travel.screen.BaseAdapter;
import com.example.dattienbkhn.travel.screen.BaseItemHolder;

import java.util.List;

/**
 * Created by dattienbkhn on 07/03/2018.
 */

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastHolder>
        implements BaseAdapter<List<ForecastWrap>> {
    private Context ctx;
    private List<ForecastWrap> forecastWraps;

    public ForecastAdapter(Context ctx, List<ForecastWrap> forecastWraps) {
        this.ctx = ctx;
        this.forecastWraps = forecastWraps;
    }

    @Override
    public ForecastHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemForecastBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(ctx),
                R.layout.item_forecast,
                parent,
                false
        );
        return new ForecastHolder(binding);
    }

    @Override
    public void onBindViewHolder(ForecastHolder holder, int position) {
        holder.setBinding(forecastWraps.get(position));
    }

    @Override
    public int getItemCount() {
        return forecastWraps.size();
    }

    @Override
    public void setData(List<ForecastWrap> data) {
        forecastWraps.clear();
        forecastWraps.addAll(data);
        notifyDataSetChanged();
    }

    public class ForecastHolder extends RecyclerView.ViewHolder implements BaseItemHolder<ForecastWrap> {
        private ItemForecastBinding binding;

        public ForecastHolder(ItemForecastBinding binding) {

            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void setBinding(ForecastWrap obj) {
            binding.setViewModel(new ItemForecastViewModel(obj));
            binding.executePendingBindings();
        }
    }
}
