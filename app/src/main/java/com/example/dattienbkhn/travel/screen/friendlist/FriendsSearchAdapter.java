package com.example.dattienbkhn.travel.screen.friendlist;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.dattienbkhn.travel.R;
import com.example.dattienbkhn.travel.databinding.ItemFriendSearchBinding;
import com.example.dattienbkhn.travel.network.message.user.UserSearch;
import com.example.dattienbkhn.travel.screen.BaseAdapter;
import com.example.dattienbkhn.travel.screen.BaseItemHolder;
import com.example.dattienbkhn.travel.screen.main.mefragment.ItemFriendViewModel;

import java.util.List;

/**
 * Created by tiendatbkhn on 25/04/2018.
 */

public class FriendsSearchAdapter extends RecyclerView.Adapter<FriendsSearchAdapter.FriendSearchHolder>
        implements BaseAdapter<List<UserSearch>> {

    private Context ctx;
    private List<UserSearch> userSearches;
    private ItemFriendViewModel.IItemFriendClick itemFriendClick;

    public FriendsSearchAdapter(Context ctx, List<UserSearch> userSearches, ItemFriendViewModel.IItemFriendClick itemFriendClick) {
        this.ctx = ctx;
        this.userSearches = userSearches;
        this.itemFriendClick = itemFriendClick;
    }

    @NonNull
    @Override
    public FriendSearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFriendSearchBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(ctx),
                R.layout.item_friend_search,
                parent,
                false
        );
        return new FriendSearchHolder(binding,itemFriendClick);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendSearchHolder holder, int position) {
        holder.setBinding(userSearches.get(position));
    }

    @Override
    public int getItemCount() {
        return userSearches.size();
    }

    public void setItemFriendClick(ItemFriendViewModel.IItemFriendClick itemFriendClick) {
        this.itemFriendClick = itemFriendClick;
    }

    @Override
    public void setData(List<UserSearch> data) {
        userSearches.clear();
        userSearches.addAll(data);
        notifyDataSetChanged();
    }

    public class FriendSearchHolder extends RecyclerView.ViewHolder implements BaseItemHolder<UserSearch> {
        private ItemFriendSearchBinding binding;
        private ItemFriendViewModel.IItemFriendClick itemFriendClick;

        public FriendSearchHolder(ItemFriendSearchBinding binding, ItemFriendViewModel.IItemFriendClick itemFriendClick) {
            super(binding.getRoot());
            this.binding = binding;
            this.itemFriendClick = itemFriendClick;
        }

        @Override
        public void setBinding(UserSearch obj) {
            binding.setViewModel(new ItemFriendsSearchViewModel(obj,itemFriendClick,binding));
            binding.executePendingBindings();
        }
    }
}
