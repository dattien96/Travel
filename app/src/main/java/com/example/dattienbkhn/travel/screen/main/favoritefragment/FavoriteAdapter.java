package com.example.dattienbkhn.travel.screen.main.favoritefragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.dattienbkhn.travel.R;
import com.example.dattienbkhn.travel.databinding.ItemFavoritePlaceBinding;
import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.screen.BaseAdapter;
import com.example.dattienbkhn.travel.screen.BaseItemHolder;
import com.example.dattienbkhn.travel.screen.main.mainfragment.ItemFamousPlaceCountryViewModel;

import java.util.List;

/**
 * Created by tiendatbkhn on 23/03/2018.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder>
        implements BaseAdapter<List<Place>> {
    private Context ctx;
    private List<Place> placeSaves;
    private ItemFamousPlaceCountryViewModel.IItemPlaceClick itemPlaceClick;
    private ItemFavoriteViewModel.IItemPopupMenu itemPopupMenu;

    public FavoriteAdapter(Context ctx, List<Place> placeSaves, ItemFamousPlaceCountryViewModel.IItemPlaceClick itemPlaceClick, ItemFavoriteViewModel.IItemPopupMenu itemPopupMenu) {
        this.ctx = ctx;
        this.placeSaves = placeSaves;
        this.itemPlaceClick = itemPlaceClick;
        this.itemPopupMenu = itemPopupMenu;
    }

    @Override
    public FavoriteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemFavoritePlaceBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(ctx),
                R.layout.item_favorite_place,
                parent,
                false
        );
        return new FavoriteHolder(binding);
    }

    @Override
    public void onBindViewHolder(FavoriteHolder holder, int position) {
        holder.setBinding(placeSaves.get(position));
    }

    @Override
    public int getItemCount() {
        return placeSaves.size();
    }

    @Override
    public void setData(List<Place> data) {
        placeSaves.clear();
        placeSaves.addAll(data);
        notifyDataSetChanged();
    }

    public void removeItem(int placeId) {
        for (int i = 0; i < placeSaves.size(); i++) {
            if (placeSaves.get(i).getPlaceId() == placeId) {
                placeSaves.remove(i);
                notifyItemRemoved(i);
                return;
            }
        }
    }

    public class FavoriteHolder extends RecyclerView.ViewHolder implements BaseItemHolder<Place> {
        private ItemFavoritePlaceBinding binding;

        public FavoriteHolder(ItemFavoritePlaceBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void setBinding(Place obj) {
            binding.setViewModel(new ItemFavoriteViewModel(obj,itemPlaceClick,itemPopupMenu,binding));
            binding.executePendingBindings();
        }
    }
}
