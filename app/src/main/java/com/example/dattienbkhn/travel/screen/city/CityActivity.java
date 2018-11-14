package com.example.dattienbkhn.travel.screen.city;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dattienbkhn.travel.R;
import com.example.dattienbkhn.travel.databinding.ActivityCityBinding;
import com.example.dattienbkhn.travel.model.app.City;
import com.example.dattienbkhn.travel.screen.city.exploreFragment.ExploreFragment;
import com.example.dattienbkhn.travel.screen.city.homeFragment.HomeFragment;
import com.example.dattienbkhn.travel.screen.city.utilFragment.contain.ContainFragment;
import com.example.dattienbkhn.travel.screen.search.SearchActivity;
import com.example.dattienbkhn.travel.utils.dialog.DialogManager;

import java.util.ArrayList;
import java.util.List;

public class CityActivity extends AppCompatActivity implements CityContract.View, ITabListener {
    public static final String CITY_ACT_FLAG = "CityActivity";
    private ActivityCityBinding binding;
    private CityViewModel mViewModel;
    private DialogManager mDialoagManager;
    private CityViewPagerAdapter mAdapter;
    private static City mCurrentCity;
    private HomeFragment homeFragment;
    private ContainFragment containFragment;
    private ExploreFragment exploreFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_city);

        Bundle bd = getIntent().getExtras().getBundle(CityActivity.CITY_ACT_FLAG);
        mCurrentCity = (City) bd.getSerializable("city");


        getHomeFragment();
        getPlayFragment();
        getContainFragment();
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(homeFragment);
        fragments.add(exploreFragment);
        fragments.add(containFragment);


        mDialoagManager = new DialogManager(this);
        mAdapter = new CityViewPagerAdapter(getSupportFragmentManager(), fragments);
        mViewModel = new CityViewModel(mDialoagManager);
        mViewModel.setCurrenCity(mCurrentCity);
        mViewModel.setViewNavigator(this);
        mViewModel.setAdapter(mAdapter);
        mViewModel.setBinding(binding);


        binding.setViewModel(mViewModel);
        setTabTitleFont();
        onInitViewModel();
    }

    public void getHomeFragment() {
        if (homeFragment == null)
            homeFragment = HomeFragment.getFragmentInstance();
        homeFragment.setCurrentCityInstance(mCurrentCity);
        homeFragment.setiTabListener(this);
    }


    public void getPlayFragment() {
        if (exploreFragment == null)
            exploreFragment = ExploreFragment.getFragmentInstance();
        exploreFragment.setCurrentCityInstance(mCurrentCity);

    }

    public void getContainFragment() {
        if (containFragment == null)
            containFragment = ContainFragment.getFragmentInstance();
        containFragment.setCurrentCityInstance(mCurrentCity);
    }

    @Override
    public void onInitViewModel() {
        mViewModel.onStart();
    }

    @Override
    public void onErrorMessage(String mes) {
        Toast.makeText(this, "" + mes, Toast.LENGTH_SHORT).show();
    }

    public void setTabTitleFont() {
        ViewGroup childTabLayout = (ViewGroup) binding.tlCity.getChildAt(0);
        for (int i = 0; i < childTabLayout.getChildCount(); i++) {
            ViewGroup viewTab = (ViewGroup) childTabLayout.getChildAt(i);
            for (int j = 0; j < viewTab.getChildCount(); j++) {
                View tabTextView = viewTab.getChildAt(j);
                if (tabTextView instanceof TextView) {
                    Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
                    ((TextView) tabTextView).setTypeface(typeface);

                }
            }
        }
    }

    @Override
    public void goBack() {
        finish();
    }

    @Override
    public void goSearchAct() {
        startActivity(new Intent(this, SearchActivity.class));
    }


    @Override
    public void onTabChange(int pos) {
        mViewModel.setTabViewPager(pos);
    }

}
