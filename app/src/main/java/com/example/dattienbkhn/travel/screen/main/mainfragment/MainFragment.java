package com.example.dattienbkhn.travel.screen.main.mainfragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dattienbkhn.travel.R;
import com.example.dattienbkhn.travel.databinding.FragmentMainBinding;
import com.example.dattienbkhn.travel.model.app.City;
import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.screen.city.CityActivity;
import com.example.dattienbkhn.travel.screen.detailsplace.PlaceActivity;
import com.example.dattienbkhn.travel.screen.search.SearchActivity;
import com.example.dattienbkhn.travel.utils.dialog.DialogManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dattienbkhn on 07/02/2018.
 */

public class MainFragment extends Fragment implements MainFrgContract.View, ItemFamousPlaceCountryViewModel.IItemPlaceClick,
        ItemTopCityViewModel.IItemCityClick {
    public static final String MAIN_FRAGMENT_TAG = "MainFragmentTag";
    private MainFrgViewModel mMainFrgViewModel;
    private FragmentMainBinding mFrgMainBinding;
    private DialogManager mDialogManager;
    private PlaceofCountryAdapter mPlaceofCountryAdapter;
    private CityAdapter mCityAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //viewmodel
        mDialogManager = new DialogManager(getActivity());
        mMainFrgViewModel = new MainFrgViewModel(mDialogManager);
        mMainFrgViewModel.setViewNavigator(this);
        mMainFrgViewModel.setFrgMainBinding(mFrgMainBinding);
        mMainFrgViewModel.mCollapseUrl.set("https://www.freevector.com/uploads/vector/preview/12939/FreeVector-Travel-Background.jpg");

        List<Place> places = new ArrayList<>();
        mPlaceofCountryAdapter = new PlaceofCountryAdapter(getActivity(), places);
        mMainFrgViewModel.setPlaceAdapter(mPlaceofCountryAdapter);
        mMainFrgViewModel.setItemPlaceClick(this);

        List<City> cities = new ArrayList<>();
        mCityAdapter = new CityAdapter(getActivity(), cities);
        mMainFrgViewModel.setCityAdapter(mCityAdapter);
        mMainFrgViewModel.setItemCityClick(this);
    }

    @Nullable
    @Override
    public android.view.View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mFrgMainBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity())
                , R.layout.fragment_main, container, false);

        mFrgMainBinding.setViewModel(mMainFrgViewModel);

        setHasOptionsMenu(true);

        android.view.View root = mFrgMainBinding.getRoot();

        onInitViewModel();

        return root;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
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
    public void goToCityAct(City obj) {
        Intent intent = new Intent(getActivity(), CityActivity.class);
        Bundle bd = new Bundle();
        bd.putSerializable("city",obj);
        intent.putExtra(CityActivity.CITY_ACT_FLAG,bd);
        startActivity(intent);
    }

    @Override
    public void goToSearchAct() {
        startActivity(new Intent(getActivity(), SearchActivity.class));
    }



    @Override
    public void onInitViewModel() {
        mMainFrgViewModel.onStart();
    }

    @Override
    public void onErrorMessage(String mes) {
        Toast.makeText(getActivity(), mes, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onItemPlaceClick(Place obj) {
        goToPlaceDetailsAct(obj);
    }

    @Override
    public void onItemCityClick(City obj) {
        goToCityAct(obj);
    }

}
