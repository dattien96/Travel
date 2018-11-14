package com.example.dattienbkhn.travel.screen.city.homeFragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dattienbkhn.travel.R;
import com.example.dattienbkhn.travel.databinding.FragmentCityHomeBinding;
import com.example.dattienbkhn.travel.model.app.City;
import com.example.dattienbkhn.travel.model.app.Image;
import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.model.weather.currentweather.WeatherObj;
import com.example.dattienbkhn.travel.screen.city.ITabListener;
import com.example.dattienbkhn.travel.screen.common.SlideImageAdapter;
import com.example.dattienbkhn.travel.screen.detailsplace.PlaceActivity;
import com.example.dattienbkhn.travel.screen.main.mainfragment.ItemFamousPlaceCountryViewModel;
import com.example.dattienbkhn.travel.screen.weather.WeatherActivity;
import com.example.dattienbkhn.travel.utils.dialog.DialogManager;

import java.util.ArrayList;
import java.util.List;

import static com.example.dattienbkhn.travel.screen.weather.WeatherActivity.WEATHER_ACT_FLAG;

/**
 * Created by dattienbkhn on 09/02/2018.
 */

public class HomeFragment extends Fragment implements HomeFrgContract.View,
        ItemFamousPlaceCountryViewModel.IItemPlaceClick {
    private HomeFrgViewModel mViewModel;
    private DialogManager mDialogManager;
    private PlaceAdapter mPlayPlaceAdapter;
    private PlaceAdapter mStayPlaceAdapter;
    private PlaceAdapter mEnterPlaceAdapter;
    private PlaceAdapter mEatDrinklaceAdapter;
    private SlideImageAdapter mImageAdapter;
    private City mCurrentCity;
    private ITabListener iTabListener;
    private FragmentCityHomeBinding binding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDialogManager = new DialogManager(getActivity());
        mViewModel = new HomeFrgViewModel(mDialogManager);
        mDialogManager.showProgressDialog();
        mViewModel.setViewNavigator(this);
        mViewModel.setCurrenCity(mCurrentCity);
        Log.e("fragment","Home");

        List<Image> images = new ArrayList<>();
        mImageAdapter = new SlideImageAdapter(getActivity(),images);
        mViewModel.setImageAdapter(mImageAdapter);

        List<Place> playPlaces = new ArrayList<>();
        mPlayPlaceAdapter = new PlaceAdapter(getActivity(),playPlaces);
        mViewModel.setPlayPlaceAdapter(mPlayPlaceAdapter);

        List<Place> eatDrinkPlaces = new ArrayList<>();
        mEatDrinklaceAdapter = new PlaceAdapter(getActivity(),eatDrinkPlaces);
        mViewModel.setEatDrinklaceAdapter(mEatDrinklaceAdapter);

        List<Place> stayPlaces = new ArrayList<>();
        mStayPlaceAdapter = new PlaceAdapter(getActivity(),stayPlaces);
        mViewModel.setStayPlaceAdapter(mStayPlaceAdapter);

        List<Place> enterPlaces = new ArrayList<>();
        mEnterPlaceAdapter = new PlaceAdapter(getActivity(),enterPlaces);
        mViewModel.setEnterPlaceAdapter(mEnterPlaceAdapter);

        //this method must call after set adapter for viewmodel
        //if call before , adapter in view is null when exe this
        mViewModel.setItemPlaceClick(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         binding = DataBindingUtil.inflate(
                LayoutInflater.from(getActivity()),
                R.layout.fragment_city_home,
                container, false
        );
        setHasOptionsMenu(true);
        mViewModel.setBinding(binding);
        binding.setViewModel(mViewModel);
        onInitViewModel();
        return binding.getRoot();
    }

    public static HomeFragment getFragmentInstance() {
        return new HomeFragment();
    }


    @Override
    public void onInitViewModel() {
        mViewModel.onStart();
    }

    @Override
    public void onErrorMessage(String mes) {
        Toast.makeText(getActivity(), "" + mes, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemPlaceClick(Place obj) {
        goToDetailsPlaceAct(obj);
    }

    @Override
    public void goToDetailsPlaceAct(Place obj) {
        Intent intent = new Intent(getActivity(), PlaceActivity.class);
        Bundle bd = new Bundle();
        bd.putSerializable("place",obj);
        intent.putExtra(PlaceActivity.PLACE_ACT_FLAG,bd);
        startActivity(intent);
    }

    @Override
    public void setCurrentCityInstance(City obj) {
        mCurrentCity = obj;
    }

    @Override
    public void goToExploreTab() {
        iTabListener.onTabChange(1);
    }

    @Override
    public void goToUtilTab() {
        iTabListener.onTabChange(2);
    }

    @Override
    public void goToWeatherAct(WeatherObj obj) {
        Intent intent = new Intent(getActivity(), WeatherActivity.class);
        Bundle bd = new Bundle();
        bd.putSerializable("weatherObj",obj);
        intent.putExtra(WEATHER_ACT_FLAG,bd);
        startActivity(intent);
    }

    public void setiTabListener(ITabListener iTabListener) {
        this.iTabListener = iTabListener;
    }
}
