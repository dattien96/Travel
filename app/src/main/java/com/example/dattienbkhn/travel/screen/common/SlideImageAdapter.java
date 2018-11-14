package com.example.dattienbkhn.travel.screen.common;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.dattienbkhn.travel.R;
import com.example.dattienbkhn.travel.databinding.ItemImageSlideBinding;
import com.example.dattienbkhn.travel.model.app.Image;
import com.example.dattienbkhn.travel.screen.BaseAdapter;

import java.util.List;

/**
 * Created by dattienbkhn on 10/02/2018.
 */

public class SlideImageAdapter extends PagerAdapter implements BaseAdapter<List<Image>> {
    private Context ctx;
    private List<Image> images;

    public SlideImageAdapter(Context ctx, List<Image> images) {
        this.ctx = ctx;
        this.images = images;
    }


    @Override
    public int getCount() {
        return images.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ItemImageSlideBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(ctx),
                R.layout.item_image_slide,
                container,
                false
        );
        ItemImageSLideViewModel mViewModel = new ItemImageSLideViewModel(images.get(position));
        binding.setViewModel(mViewModel);
        container.addView(binding.getRoot());
        return binding.getRoot();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }

    @Override
    public void setData(List<Image> data) {
        this.images.clear();
        this.images.addAll(data);
        notifyDataSetChanged();
    }
}
