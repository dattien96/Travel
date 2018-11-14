package com.example.dattienbkhn.travel.screen.city;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by dattienbkhn on 09/02/2018.
 */

public class CityViewPagerAdapter extends FragmentStatePagerAdapter {
    private static final int mTabHome = 0;
    private static final int mTabExplore = 1;
    private static final int mTabUtil = 2;
    private List<Fragment> fragments;


    public CityViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case mTabHome:
                return fragments.get(0);

            case mTabExplore:
                return fragments.get(1);

            case mTabUtil:
                return fragments.get(2);

        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    public void reloadTab(int pos) {

    }

    @Override
    public Parcelable saveState() {
        return super.saveState();
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        super.restoreState(state, loader);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case mTabHome:
                return "Home";

            case mTabExplore:
                return "Explore";

            case mTabUtil:
                return "Near";
        }
        return null;
    }
}
