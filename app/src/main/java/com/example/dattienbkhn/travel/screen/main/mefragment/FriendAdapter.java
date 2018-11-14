package com.example.dattienbkhn.travel.screen.main.mefragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dattienbkhn.travel.R;
import com.example.dattienbkhn.travel.databinding.ItemFriendBinding;
import com.example.dattienbkhn.travel.model.app.Friend;
import com.example.dattienbkhn.travel.screen.BaseAdapter;
import com.example.dattienbkhn.travel.screen.BaseItemHolder;

import java.util.List;

/**
 * Created by tiendatbkhn on 02/04/2018.
 */

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendHolder>
        implements BaseAdapter<List<Friend>> {
    private Context ctx;
    private List<Friend> friends;
    private ItemFriendViewModel.IItemFriendClick itemFriendClick;

    public FriendAdapter(Context ctx, List<Friend> friends,ItemFriendViewModel.IItemFriendClick itemFriendClick) {
        this.ctx = ctx;
        this.friends = friends;
        this.itemFriendClick = itemFriendClick;
    }

    @NonNull
    @Override
    public FriendHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFriendBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(ctx),
                R.layout.item_friend,
                parent,false
        );
        return new FriendHolder(binding,itemFriendClick);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendHolder holder, int position) {
        holder.setBinding(friends.get(position));
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    @Override
    public void setData(List<Friend> data) {
        friends.clear();
        friends.addAll(data);
        notifyDataSetChanged();
    }

    public class FriendHolder extends RecyclerView.ViewHolder implements BaseItemHolder<Friend> {
        private ItemFriendBinding binding;
        private ItemFriendViewModel.IItemFriendClick itemFriendClick;

        public FriendHolder(ItemFriendBinding binding,ItemFriendViewModel.IItemFriendClick itemFriendClick) {
            super(binding.getRoot());
            this.binding = binding;
            this.itemFriendClick = itemFriendClick;
        }

        @Override
        public void setBinding(Friend obj) {
            ItemFriendViewModel vm = new ItemFriendViewModel(obj,binding,itemFriendClick);
            binding.setViewModel(vm);
            binding.executePendingBindings();
            vm.onStart();
        }
    }
}
