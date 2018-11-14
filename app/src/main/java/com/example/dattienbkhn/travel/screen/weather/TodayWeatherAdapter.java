package com.example.dattienbkhn.travel.screen.weather;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dattienbkhn.travel.R;
import com.example.dattienbkhn.travel.databinding.ItemTodayWeatherBinding;
import com.example.dattienbkhn.travel.model.weather.forecast.ForecastWrap;
import com.example.dattienbkhn.travel.screen.BaseAdapter;
import com.example.dattienbkhn.travel.screen.BaseItemHolder;

import java.util.List;

/**
 * Created by dattienbkhn on 08/03/2018.
 */

public class TodayWeatherAdapter extends RecyclerView.Adapter<TodayWeatherAdapter.TodayHolder>
        implements BaseAdapter<List<ForecastWrap>> {
    private Context ctx;
    private List<ForecastWrap> forecastWraps;

    public TodayWeatherAdapter(Context ctx, List<ForecastWrap> forecastWraps) {
        this.ctx = ctx;
        this.forecastWraps = forecastWraps;
    }

    @Override
    public TodayHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemTodayWeatherBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(ctx),
                R.layout.item_today_weather,
                parent,
                false
        );
        return new TodayHolder(binding);
    }

    @Override
    public void onBindViewHolder(TodayHolder holder, int position) {
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

    public class TodayHolder extends RecyclerView.ViewHolder implements BaseItemHolder<ForecastWrap> {
        private ItemTodayWeatherBinding binding;

        public TodayHolder(ItemTodayWeatherBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void setBinding(ForecastWrap obj) {
            binding.setViewModel(new ItemTodayWeatherViewModel(obj));
            binding.executePendingBindings();
        }
    }
}
