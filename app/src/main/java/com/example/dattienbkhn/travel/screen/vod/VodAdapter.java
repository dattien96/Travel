package com.example.dattienbkhn.travel.screen.vod;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.dattienbkhn.travel.R;
import com.example.dattienbkhn.travel.databinding.ItemVodBinding;
import com.example.dattienbkhn.travel.screen.BaseAdapter;
import com.example.dattienbkhn.travel.screen.BaseItemHolder;

import java.util.List;

/**
 * Created by tiendatbkhn on 29/04/2018.
 */

public class VodAdapter extends RecyclerView.Adapter<VodAdapter.VodHolder> implements BaseAdapter<List<String>> {
    private Context ctx;
    private List<String> vodNames;
    private ItemVodViewModel.IVodItemCLick itemCLick;

    public VodAdapter(Context ctx, List<String> vodNames, ItemVodViewModel.IVodItemCLick itemCLick) {
        this.ctx = ctx;
        this.vodNames = vodNames;
        this.itemCLick = itemCLick;
    }

    @NonNull
    @Override
    public VodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemVodBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(ctx),
                R.layout.item_vod,
                parent,
                false
        );
        return new VodHolder(binding, itemCLick);
    }

    @Override
    public void onBindViewHolder(@NonNull VodHolder holder, int position) {
        holder.setBinding(vodNames.get(position));
    }

    @Override
    public int getItemCount() {
        return vodNames.size();
    }

    @Override
    public void setData(List<String> data) {
        vodNames.clear();
        vodNames.addAll(data);
        notifyDataSetChanged();
    }

    public class VodHolder extends RecyclerView.ViewHolder implements BaseItemHolder<String> {
        private ItemVodBinding binding;
        private ItemVodViewModel.IVodItemCLick itemCLick;

        public VodHolder(ItemVodBinding binding, ItemVodViewModel.IVodItemCLick itemCLick) {
            super(binding.getRoot());
            this.binding = binding;
            this.itemCLick = itemCLick;
        }

        @Override
        public void setBinding(String obj) {
            binding.setViewModel(new ItemVodViewModel(obj, itemCLick,binding));
            binding.executePendingBindings();
        }
    }
}
