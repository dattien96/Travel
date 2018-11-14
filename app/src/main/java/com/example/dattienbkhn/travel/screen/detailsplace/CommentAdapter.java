package com.example.dattienbkhn.travel.screen.detailsplace;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dattienbkhn.travel.R;
import com.example.dattienbkhn.travel.databinding.ItemCommentBinding;
import com.example.dattienbkhn.travel.model.app.Comment;
import com.example.dattienbkhn.travel.screen.BaseAdapter;
import com.example.dattienbkhn.travel.screen.BaseItemHolder;

import java.util.List;

/**
 * Created by tiendatbkhn on 20/03/2018.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommnetHolder>
        implements BaseAdapter<List<Comment>> {

    private Context ctx;
    private List<Comment> comments;
    private ItemCommentViewModel.IItemUserClick itemUserClick;

    public CommentAdapter(Context ctx, List<Comment> comments,ItemCommentViewModel.IItemUserClick itemUserClick) {
        this.ctx = ctx;
        this.comments = comments;
        this.itemUserClick = itemUserClick;
    }

    @Override
    public CommnetHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemCommentBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(ctx),
                R.layout.item_comment,
                parent,
                false
        );
        return new CommnetHolder(binding,itemUserClick);
    }

    @Override
    public void onBindViewHolder(CommnetHolder holder, int position) {
        holder.setBinding(comments.get(position));
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    @Override
    public void setData(List<Comment> data) {
        comments.clear();
        comments.addAll(data);
        notifyDataSetChanged();
    }


    public class CommnetHolder extends RecyclerView.ViewHolder implements BaseItemHolder<Comment> {
        private ItemCommentBinding binding;
        private ItemCommentViewModel.IItemUserClick itemUserClick;

        public CommnetHolder(ItemCommentBinding binding,ItemCommentViewModel.IItemUserClick itemUserClick) {
            super(binding.getRoot());
            this.binding = binding;
            this.itemUserClick = itemUserClick;
        }

        @Override
        public void setBinding(Comment obj) {
            ItemCommentViewModel vm = new ItemCommentViewModel(obj,binding,itemUserClick);
            binding.setViewModel(vm);
            binding.executePendingBindings();
            vm.onStart();
        }
    }
}
