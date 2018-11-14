package com.example.dattienbkhn.travel.screen.city.utilFragment.contain;

import android.Manifest;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dattienbkhn.travel.R;
import com.example.dattienbkhn.travel.databinding.FragmentLocationPermissionBinding;
import com.example.dattienbkhn.travel.model.app.City;
import com.example.dattienbkhn.travel.screen.city.utilFragment.UtilFragment;
import com.example.dattienbkhn.travel.utils.Constant;

/**
 * Created by tiendatbkhn on 09/04/2018.
 * This frg use as tab 3 of city viewpager
 * We use this frg to check permission location
 * If false -> show noti to use
 * If true we will replace UtilFragment in to layout of this frg
 */

public class ContainFragment extends Fragment implements ContainFrgContract.View {
    private FragmentLocationPermissionBinding binding;
    private ContainFrgViewModel mViewModel;
    private UtilFragment utilFragment;
    private static City mCurrentCity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ContainFrgViewModel();
        mViewModel.setViewNavigator(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(getActivity()),
                R.layout.fragment_location_permission,
                container,
                false
        );
        onInitViewModel();
        binding.setViewModel(mViewModel);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
        } else {
            Log.e("contain_frg", " : run onResume");
            onInitViewModel();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
      /*  UtilFragment frg = (UtilFragment) getFragmentManager().findFragmentById(R.id.frame_util_frg);

        if (frg == null || !frg.isInLayout()) {
            Log.e("contain_frg", " : frg chua ton tai --> khoi tao");
        }
        else {
            Log.e("contain_frg", " : frg da ton tai");
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.remove(frg);
            transaction.commit();
        }*/
    }

    @Override
    public void onInitViewModel() {
        mViewModel.onStart();
        checkPermission();
    }

    @Override
    public void checkPermission() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    Constant.MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

            mViewModel.updatePermission(false);
        } else {
            mViewModel.updatePermission(true);
            initUtilFrg();
        }
    }

    @Override
    public void onErrorMessage(String mes) {

    }

    public void getUtilFragment() {
        if (utilFragment == null) {
            utilFragment = UtilFragment.getFragmentInstance();
            utilFragment.setCurrentCityInstance(mCurrentCity);
        }
    }

    @Override
    public void initUtilFrg() {
        getUtilFragment();
        FragmentTransaction transaction = null;

        UtilFragment frg = (UtilFragment) getFragmentManager().findFragmentByTag("util_frg");
        transaction = getFragmentManager().beginTransaction();
        if (frg != null) {
            transaction.remove(frg);
            transaction.commit();
        }

        transaction = getFragmentManager().beginTransaction();
        Log.e("contain_frg", " : frg chua ton tai --> khoi tao");
        transaction.replace(R.id.frame_util_frg, utilFragment, "util_frg");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public static ContainFragment getFragmentInstance() {
        return new ContainFragment();
    }

    public static void setCurrentCityInstance(City mCurrentCity) {
        ContainFragment.mCurrentCity = mCurrentCity;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constant.MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "Permission was granted!", Toast.LENGTH_LONG).show();
                    onInitViewModel();
                } else {
                    Toast.makeText(getActivity(), "Permission denied!", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}
