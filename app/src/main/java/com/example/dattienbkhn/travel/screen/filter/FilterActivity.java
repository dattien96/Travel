package com.example.dattienbkhn.travel.screen.filter;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.dattienbkhn.travel.R;
import com.example.dattienbkhn.travel.databinding.ActivityFilterBinding;


public class FilterActivity extends AppCompatActivity implements FilterContract.View {
    private ActivityFilterBinding binding;
    private FilterViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_filter);
        mViewModel = new FilterViewModel();
        mViewModel.setViewNavigator(this);
        binding.setViewModel(mViewModel);
        onInitViewModel();
    }

    @Override
    public void onInitViewModel() {
        mViewModel.onStart();
    }

    @Override
    public void onErrorMessage(String mes) {
        Toast.makeText(this, ""+mes, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void screenFinish() {
        this.finish();
    }
}
