package com.example.dattienbkhn.travel.screen.search;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.dattienbkhn.travel.R;
import com.example.dattienbkhn.travel.databinding.ActivitySearchBinding;
import com.example.dattienbkhn.travel.model.app.City;
import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.model.app.Region;
import com.example.dattienbkhn.travel.screen.BaseViewNavigator;
import com.example.dattienbkhn.travel.screen.city.CityActivity;
import com.example.dattienbkhn.travel.screen.detailsplace.PlaceActivity;
import com.example.dattienbkhn.travel.utils.dialog.DialogManager;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements SearchContract.View {
    private ActivitySearchBinding binding;
    private SearchModelView mViewModel;
    private DialogManager mDialogManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);

        mDialogManager = new DialogManager(this);
        mViewModel = new SearchModelView(mDialogManager);
        mViewModel.setViewNavigator(this);
        mViewModel.setBinding(binding);
        mViewModel.setRegionAdapter(new RegionAdapter(this,new ArrayList<Region>()));
        mViewModel.setPlaceSearchAdapter(new PlaceSearchAdapter(this,new ArrayList<Place>()));
        mViewModel.setCitySearchAdapter(new CitySearchAdapter(this,new ArrayList<City>()));
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
    public void goToCityAct(City obj) {
        Intent intent = new Intent(this, CityActivity.class);
        Bundle bd = new Bundle();
        bd.putSerializable("city",obj);
        intent.putExtra(CityActivity.CITY_ACT_FLAG,bd);
        startActivity(intent);
    }

    @Override
    public void goToPlaceDetailsAct(Place obj) {
        Intent intent = new Intent(this, PlaceActivity.class);
        Bundle bd = new Bundle();
        bd.putSerializable("place",obj);
        intent.putExtra(PlaceActivity.PLACE_ACT_FLAG,bd);
        startActivity(intent);
    }
}
