package com.example.dattienbkhn.travel.screen.detailsplace;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.dattienbkhn.travel.R;
import com.example.dattienbkhn.travel.database.sharedPreference.TravelSharedPreference;
import com.example.dattienbkhn.travel.databinding.ActivityPlaceBinding;
import com.example.dattienbkhn.travel.model.app.Comment;
import com.example.dattienbkhn.travel.model.app.Image;
import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.model.app.PlaceInfor;
import com.example.dattienbkhn.travel.screen.comment.CommentActivity;
import com.example.dattienbkhn.travel.screen.common.SlideImageAdapter;
import com.example.dattienbkhn.travel.screen.detailsmap.MapActivity;
import com.example.dattienbkhn.travel.screen.friend.FriendActivity;
import com.example.dattienbkhn.travel.screen.profile.ProfileActivity;
import com.example.dattienbkhn.travel.utils.Constant;
import com.example.dattienbkhn.travel.utils.dialog.DialogManager;

import java.util.ArrayList;
import java.util.List;

import static com.example.dattienbkhn.travel.screen.profile.ProfileActivity.PROFILE_ACT_FLAG;

public class PlaceActivity extends AppCompatActivity implements PlaceContract.View, ItemCommentViewModel.IItemUserClick {
    public static final String PLACE_ACT_FLAG = "PlaceActivity";
    private PlaceViewModel mViewModel;
    private Place mCurrentPlace;
    private DialogManager mDialogManager;
    private SlideImageAdapter mImageAdapter;
    private CommentAdapter mCommentAdapter;

    private Place placeFromVm;
    private PlaceInfor placeInforFromVm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityPlaceBinding binding = DataBindingUtil.
                setContentView(this, R.layout.activity_place);

        mDialogManager = new DialogManager(this);
        mViewModel = new PlaceViewModel(mDialogManager);
        mViewModel.setViewNavigator(this);

        List<Image> images = new ArrayList<>();
        mImageAdapter = new SlideImageAdapter(this, images);
        mViewModel.setImageAdapter(mImageAdapter);

        List<Comment> comments = new ArrayList<>();
        mCommentAdapter = new CommentAdapter(this, comments, this);
        mViewModel.setCommentAdapter(mCommentAdapter);

        Bundle bd = getIntent().getExtras().getBundle(PlaceActivity.PLACE_ACT_FLAG);
        mCurrentPlace = (Place) bd.getSerializable("place");
        mViewModel.setCurrentPlace(mCurrentPlace);

        mViewModel.setBinding(binding);
        binding.setViewModel(mViewModel);
        onInitViewModel();
    }

    @Override
    public void onInitViewModel() {
        //because this act can be called from tab favorite , so place obj does not have full data
        if (mCurrentPlace.getPlaceImageMapUrl() == null) {
            //If this attr null mean this act run from save place, we have to reload full obj from network
            mViewModel.reloadPlaceData(mCurrentPlace.getPlaceId());
        } else {
            //if place obj is full data, run vm.onstart
            mViewModel.onStart();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewModel.onStop();
    }

    @Override
    public void onErrorMessage(String mes) {
        Toast.makeText(this, "" + mes, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goBack() {
        finish();
    }

    @Override
    public void goToCameraSurface() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.onResume();
    }

    @Override
    public void goToMapAct(Place obj, PlaceInfor placeInfor) {
        Intent intent = new Intent(this, MapActivity.class);
        Bundle bd = new Bundle();
        bd.putSerializable("place", obj);
        bd.putSerializable("placeinfor", placeInfor);
        intent.putExtra(MapActivity.MAP_ACT_FLAG, bd);
        startActivity(intent);
    }

    @Override
    public void goToCommentAct(int accId, Place obj) {
        Intent intent = new Intent(this, CommentActivity.class);
        Bundle bd = new Bundle();
        bd.putInt("accId", accId);
        bd.putSerializable("place", obj);
        intent.putExtra(CommentActivity.CMT_ACT_FLAG, bd);
        startActivity(intent);
    }

    @Override
    public void goToFriendAct(int accId) {
        Intent intent = new Intent(this, FriendActivity.class);
        /*Bundle bd = new Bundle();
        bd.putInt("accId", accId);*/
        intent.putExtra(FriendActivity.FRIEND_ACT_FLAG, accId);
        startActivity(intent);
    }

    @Override
    public void goToProfileAct(int accId, String accType) {
        Intent intent = new Intent(this, ProfileActivity.class);
        Bundle bd = new Bundle();
        bd.putInt("accId", accId);
        bd.putString("accType", accType);
        intent.putExtra(PROFILE_ACT_FLAG, bd);
        startActivity(intent);
    }

    @Override
    public void onLocationPermissionCheck(Place obj, PlaceInfor placeInfor) {
        placeFromVm = obj;
        placeInforFromVm = placeInfor;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    Constant.MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

            return;
        }

        goToMapAct(placeFromVm, placeInforFromVm);
    }

    @Override
    public void onUserItemClick(int accId) {
        SharedPreferences prefs = TravelSharedPreference.getINSTANCE();
        if (prefs.getBoolean(Constant.SHARED_LOGIN_STATE, false) == true) {
            if (accId != prefs.getInt(Constant.SHARED_LOGIN_ACC_ID, -1)) {
                goToFriendAct(accId);
            } else {
                String accType = prefs.getString(Constant.SHARED_LOGIN_TYPE, "");
                goToProfileAct(accId, accType);
            }
        } else {
            goToFriendAct(accId);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constant.MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    goToMapAct(placeFromVm, placeInforFromVm);

                } else {
                    Toast.makeText(this, "Permission denied!", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}
