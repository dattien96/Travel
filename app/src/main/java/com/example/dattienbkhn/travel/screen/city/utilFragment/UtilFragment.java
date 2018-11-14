package com.example.dattienbkhn.travel.screen.city.utilFragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dattienbkhn.travel.R;
import com.example.dattienbkhn.travel.databinding.FragmentCityUtilBinding;
import com.example.dattienbkhn.travel.model.app.City;
import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.model.app.PlaceInfor;
import com.example.dattienbkhn.travel.screen.detailsmap.MapActivity;
import com.example.dattienbkhn.travel.screen.detailsplace.PlaceActivity;
import com.example.dattienbkhn.travel.utils.Constant;
import com.example.dattienbkhn.travel.utils.dialog.DialogManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by dattienbkhn on 09/02/2018.
 */

public class UtilFragment extends Fragment implements UtilFrgContract.View, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    private LocationRequest locationRequest;
    private GoogleApiClient gac;

    private FragmentCityUtilBinding binding;
    private UtilFrgViewModel mViewModel;
    private DialogManager mDialogManger;
    private City mCurrentCity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isGooglePlayServicesAvailable();

        //set up location request
        locationRequest = new LocationRequest();
        locationRequest.setInterval(30000);
        locationRequest.setFastestInterval(30000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        //set location api cho ggApiClient callBack <phan nay phai lam trong onCreat>
        gac = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();


        //viewmodel
        mDialogManger = new DialogManager(getActivity());
        mViewModel = new UtilFrgViewModel(mDialogManger);
        mViewModel.setViewNavigator(this);
        mViewModel.setCurrentCity(mCurrentCity);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(
                LayoutInflater.from(getActivity()),
                R.layout.fragment_city_util,
                container,
                false
        );

        binding.setViewModel(mViewModel);
        onInitViewModel();
        return binding.getRoot();

    }

    @Override
    public void onResume() {
        super.onResume();
        checkStateLocation();
    }

    @Override
    public void onStart() {
        gac.connect();
        super.onStart();
    }

    @Override
    public void onStop() {
        gac.disconnect();
        super.onStop();
    }

    @Override
    public void onInitViewModel() {
        checkStateLocation();
        mViewModel.onStart();
    }

    @Override
    public void onErrorMessage(String mes) {
        Toast.makeText(getActivity(), "" + mes, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goToPlaceDetailsAct(Place obj) {
        Intent intent = new Intent(getActivity(), PlaceActivity.class);
        Bundle bd = new Bundle();
        bd.putSerializable("place", obj);
        intent.putExtra(PlaceActivity.PLACE_ACT_FLAG, bd);
        startActivity(intent);
    }

    @Override
    public void goToMapAct(Place obj, PlaceInfor placeInfor) {
        Intent intent = new Intent(getActivity(), MapActivity.class);
        Bundle bd = new Bundle();
        bd.putSerializable("place", obj);
        bd.putSerializable("placeinfor", placeInfor);
        intent.putExtra(MapActivity.MAP_ACT_FLAG, bd);
        startActivity(intent);
    }

    @Override
    public void checkStateLocation() {
        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            mViewModel.updateStateLocation(gps_enabled);
        } catch (Exception ex) {
        }
    }

    @Override
    public void goToAppSetting() {
        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        getActivity().startActivity(myIntent);
    }


    public void setCurrentCityInstance(City mCurrentCity) {
        this.mCurrentCity = mCurrentCity;
    }

    public static UtilFragment getFragmentInstance() {
        return new UtilFragment();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    Constant.MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

            return;
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(gac, locationRequest, mViewModel);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private boolean isGooglePlayServicesAvailable() {
        final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(getActivity());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(getActivity(), resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Toast.makeText(getActivity(), "Google Play Service is not supported !", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constant.MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "Permission was granted!", Toast.LENGTH_LONG).show();

                    try {
                        LocationServices.FusedLocationApi.requestLocationUpdates(
                                gac, locationRequest, mViewModel);


                    } catch (SecurityException e) {
                        Toast.makeText(getActivity(), "SecurityException:\n" + e.toString(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Permission denied!", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}
