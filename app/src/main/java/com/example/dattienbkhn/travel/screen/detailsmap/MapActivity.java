package com.example.dattienbkhn.travel.screen.detailsmap;

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
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.dattienbkhn.travel.R;
import com.example.dattienbkhn.travel.databinding.ActivityMapBinding;
import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.model.app.PlaceInfor;
import com.example.dattienbkhn.travel.network.message.map.direction.Step;
import com.example.dattienbkhn.travel.screen.camera.CameraActivity;
import com.example.dattienbkhn.travel.utils.Constant;
import com.example.dattienbkhn.travel.utils.dialog.DialogManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;


public class MapActivity extends AppCompatActivity implements MapContract.View,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    public static final String MAP_ACT_FLAG = "MapActivity";

    private LocationRequest locationRequest;
    private GoogleApiClient gac;
    private ActivityMapBinding binding;
    private MapViewModel mViewModel;
    private Place mCurrentPlace;
    private PlaceInfor mPlaceInfor;
    private DialogManager mDialogManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_map);

        mDialogManager = new DialogManager(this);
        mViewModel = new MapViewModel(mDialogManager);
        mViewModel.setViewNavigator(this);
        DirectionAdapter adapter = new DirectionAdapter(this,new ArrayList<Step>());
        mViewModel.setDirectionAdapter(adapter);
        mViewModel.setBinding(binding);


        Bundle bd = getIntent().getExtras().getBundle(MapActivity.MAP_ACT_FLAG);
        mCurrentPlace = (Place) bd.getSerializable("place");
        mViewModel.setCurrentPlace(mCurrentPlace);
        mPlaceInfor = (PlaceInfor) bd.getSerializable("placeinfor");
        mViewModel.setPlaceInfor(mPlaceInfor);
        /*mCurrentPlace = new Place();
        mCurrentPlace.setLongitude(105.846872);
        mCurrentPlace.setLatitude(21.006251);
        mCurrentPlace.setPlaceName("Test");
        mCurrentPlace.setTypeGenerate(Constant.SHARED_PLACE_FOODIES_TYPE);
        mCurrentPlace.setGroupType(1);
        mViewModel.setCurrentPlace(mCurrentPlace);
        mPlaceInfor = new PlaceInfor();
        mPlaceInfor.setName("Tran dai nghia");
        mViewModel.setPlaceInfor(mPlaceInfor);*/
        isGooglePlayServicesAvailable();

        //set up location request
        locationRequest = new LocationRequest();
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        //set location api cho ggApiClient callBack <phan nay phai lam trong onCreat>
        gac = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        binding.setViewModel(mViewModel);
        onInitViewModel();
    }

    @Override
    public void onInitViewModel() {
        checkStateLocation();
        mViewModel.onStart();
    }

    @Override
    public void onErrorMessage(String mes) {
        Toast.makeText(this, ""+mes, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        gac.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        gac.disconnect();
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        checkStateLocation();
    }

    private boolean isGooglePlayServicesAvailable() {
        final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Toast.makeText(this, "Google Play Service is not supported !", Toast.LENGTH_SHORT).show();
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapActivity.this,
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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constant.MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MapActivity.this, "Permission was granted!", Toast.LENGTH_LONG).show();

                    try {
                        LocationServices.FusedLocationApi.requestLocationUpdates(
                                gac, locationRequest, mViewModel);
                    } catch (SecurityException e) {
                        Toast.makeText(MapActivity.this, "SecurityException:\n" + e.toString(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MapActivity.this, "Permission denied!", Toast.LENGTH_LONG).show();
                }
                return;
            }

            case Constant.MY_PERMISSIONS_REQUEST_ACCESS_CAMERA:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(new Intent(this, CameraActivity.class));
                }
                else {
                    Toast.makeText(this, "Permission denied!", Toast.LENGTH_LONG).show();
                }

                return;
            }
        }
    }

    @Override
    public void goToCameraAct() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    Constant.MY_PERMISSIONS_REQUEST_ACCESS_CAMERA);

            return;
        }

        startActivity(new Intent(this, CameraActivity.class));
    }

    @Override
    public void checkStateLocation() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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
        startActivity(myIntent);
    }
}
