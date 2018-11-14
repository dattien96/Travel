package com.example.dattienbkhn.travel.screen.lovedplace;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.dattienbkhn.travel.R;
import com.example.dattienbkhn.travel.databinding.ActivityLovedPlaceBinding;
import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.screen.detailsplace.PlaceActivity;
import com.example.dattienbkhn.travel.screen.main.mainfragment.ItemFamousPlaceCountryViewModel;
import com.example.dattienbkhn.travel.utils.dialog.DialogManager;

import java.util.ArrayList;

public class LovedPlaceActivity extends AppCompatActivity implements LovedPlaceContract.View,
        ItemFamousPlaceCountryViewModel.IItemPlaceClick {
    public static final String LOVED_PLACE_ACT_FLAG = "LovedPlaceActivity";
    private ActivityLovedPlaceBinding binding;
    private DialogManager mDialogManager;
    private LovedPlaceViewModel mViewModel;
    private int mCurAccId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_loved_place);

        Bundle bd = getIntent().getExtras().getBundle(LOVED_PLACE_ACT_FLAG);
        mCurAccId = bd.getInt("accId",-1);

        mDialogManager = new DialogManager(this);
        mViewModel = new LovedPlaceViewModel(mDialogManager);
        mViewModel.setBinding(binding);
        mViewModel.setViewNavigator(this);
        mViewModel.setLovedPlaceAdapter(new LovedPlaceAdapter(this, new ArrayList<Place>(), this));
        mViewModel.setmCurrentAccId(mCurAccId);

        onInitViewModel();
        binding.setViewModel(mViewModel);
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
    public void goFinish() {
        finish();
    }

    @Override
    public void goToPlaceDetailsAct(Place obj) {
        Intent intent = new Intent(this, PlaceActivity.class);
        Bundle bd = new Bundle();
        bd.putSerializable("place", obj);
        intent.putExtra(PlaceActivity.PLACE_ACT_FLAG, bd);
        startActivity(intent);
    }

    @Override
    public void onItemPlaceClick(Place obj) {
        goToPlaceDetailsAct(obj);
    }
}
