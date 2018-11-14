package com.example.dattienbkhn.travel.screen.city.exploreFragment;

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
import com.example.dattienbkhn.travel.databinding.FragmentCityExploreBinding;
import com.example.dattienbkhn.travel.model.app.City;
import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.screen.common.PlaceAdapterForAllTabMap;
import com.example.dattienbkhn.travel.screen.detailsplace.PlaceActivity;
import com.example.dattienbkhn.travel.screen.filter.FilterActivity;
import com.example.dattienbkhn.travel.screen.main.mainfragment.ItemFamousPlaceCountryViewModel;
import com.example.dattienbkhn.travel.utils.dialog.DialogManager;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dattienbkhn on 09/02/2018.
 */

public class ExploreFragment extends Fragment implements
        ExploreFrgContract.View,ItemFamousPlaceCountryViewModel.IItemPlaceClick {

    private GoogleMap mMap;
    private MapView mMapView;
    private FragmentCityExploreBinding binding;
    private ExploreFrgViewModel mViewModel;
    private DialogManager mDialogManger;
    private PlaceAdapterForAllTabMap mPlaceAdapter;
    private City mCurrentCity;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("fragment","Play");

        //viewmodel
        mDialogManger = new DialogManager(getActivity());
        mViewModel = new ExploreFrgViewModel(mDialogManger);
        mViewModel.setViewNavigator(this);
        mViewModel.setCurrentCity(mCurrentCity);

        List<Place> places = new ArrayList<>();
        mPlaceAdapter = new PlaceAdapterForAllTabMap(getActivity(),places);
        mViewModel.setPlaceAdapter(mPlaceAdapter);

        //call after adapter in viewmodel init to avoid null
        mViewModel.setItemPlaceClick(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         binding = DataBindingUtil.inflate(
                LayoutInflater.from(getActivity()),
                R.layout.fragment_city_explore,
                container,
                false
        );
        binding.setViewModel(mViewModel);
        mViewModel.setBinding(binding);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        onInitViewModel();
    }

    @Override
    public void onPause() {
        super.onPause();
        mViewModel.deleteBorderItem();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public static ExploreFragment getFragmentInstance() {
        return new ExploreFragment();
    }

    @Override
    public void onInitViewModel() {
        mViewModel.onStart();
    }

    @Override
    public void onErrorMessage(String mes) {
        Toast.makeText(getActivity(), ""+mes, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goToPlaceDetailsAct(Place obj) {
        Intent intent = new Intent(getActivity(), PlaceActivity.class);
        Bundle bd = new Bundle();
        bd.putSerializable("place",obj);
        intent.putExtra(PlaceActivity.PLACE_ACT_FLAG,bd);
        startActivity(intent);
    }

    @Override
    public void goToFilterAct() {
        startActivity(new Intent(getActivity(), FilterActivity.class));
    }

    public void setCurrentCityInstance(City mCurrentCity) {
        this.mCurrentCity = mCurrentCity;
    }

    @Override
    public void onItemPlaceClick(Place obj) {
        goToPlaceDetailsAct(obj);
    }
}
