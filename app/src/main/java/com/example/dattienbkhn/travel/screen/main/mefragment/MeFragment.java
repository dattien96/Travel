package com.example.dattienbkhn.travel.screen.main.mefragment;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dattienbkhn.travel.R;
import com.example.dattienbkhn.travel.database.sharedPreference.TravelSharedPreference;
import com.example.dattienbkhn.travel.databinding.FragmentMeBinding;
import com.example.dattienbkhn.travel.model.app.Friend;
import com.example.dattienbkhn.travel.screen.friend.FriendActivity;
import com.example.dattienbkhn.travel.screen.friendlist.FriendListActivity;
import com.example.dattienbkhn.travel.screen.lovedplace.LovedPlaceActivity;
import com.example.dattienbkhn.travel.screen.main.mefragment.register.RegisterActivity;
import com.example.dattienbkhn.travel.screen.profile.ProfileActivity;
import com.example.dattienbkhn.travel.screen.setting.SettingActivity;
import com.example.dattienbkhn.travel.screen.stream.livestream.LiveVideoBroadcasterActivity;
import com.example.dattienbkhn.travel.screen.vod.VodActivity;
import com.example.dattienbkhn.travel.utils.Constant;
import com.example.dattienbkhn.travel.utils.dialog.DialogManager;
import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;

import static com.example.dattienbkhn.travel.screen.profile.ProfileActivity.PROFILE_ACT_FLAG;

/**
 * Created by dattienbkhn on 09/02/2018.
 */

public class MeFragment extends Fragment implements MeFrgContract.View, ItemFriendViewModel.IItemFriendClick {
    private static final int RC_SIGN_IN = 1010;
    public static final String ME_FRAGMENT_TAG = "MeFragmentTag";
    private MeFrgViewModel mViewModel;
    private DialogManager mDialogManager;
    private FragmentMeBinding binding;
    //facebook
    private CallbackManager callbackManager;
    //google
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions gso;

    private int accId;
    private ISocketConnect iSocketConnect;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //viewmodel
        mDialogManager = new DialogManager(getActivity());
        mViewModel = new MeFrgViewModel(mDialogManager);
        mViewModel.setViewNavigator(this);
        mViewModel.setFriendAdapter(new FriendAdapter(getActivity(), new ArrayList<Friend>(), this));


        //facebook
        callbackManager = CallbackManager.Factory.create();
        mViewModel.setCallbackManager(callbackManager);

        //google
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()),
                R.layout.fragment_me,
                container, false);

        setHasOptionsMenu(true);
        mViewModel.setBinding(binding);
        binding.setViewModel(mViewModel);
        binding.loginButton.setFragment(this);
        binding.loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends"));

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        onInitViewModel();
    }



    public void setSocketConnect(ISocketConnect iSocketConnect) {
        this.iSocketConnect = iSocketConnect;
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
    public void goToRegisterAct() {
        startActivity(new Intent(getActivity(), RegisterActivity.class));
    }

    @Override
    public void goToSettingAct() {
        startActivity(new Intent(getActivity(), SettingActivity.class));
    }

    @Override
    public void loginGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //facebook
        callbackManager.onActivityResult(requestCode, resultCode, data);

        //google
        // NearyByPlace returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            mViewModel.handleGoogleSignInResult(task);
        }
    }

    @Override
    public void goToFriendAct(int accId) {
        Intent intent = new Intent(getActivity(), FriendActivity.class);
        /*Bundle bd = new Bundle();
        bd.putInt("accId", accId);*/
        intent.putExtra(FriendActivity.FRIEND_ACT_FLAG, accId);
        startActivity(intent);
    }

    @Override
    public void goToProfileAct(int accId, String accType) {
        Intent intent = new Intent(getActivity(), ProfileActivity.class);
        Bundle bd = new Bundle();
        bd.putInt("accId", accId);
        bd.putString("accType", accType);
        intent.putExtra(PROFILE_ACT_FLAG, bd);
        startActivity(intent);
    }

    @Override
    public void goToLovedPlaceAct(int accId) {
        Intent intent = new Intent(getActivity(), LovedPlaceActivity.class);
        Bundle bd = new Bundle();
        bd.putInt("accId", accId);
        intent.putExtra(LovedPlaceActivity.LOVED_PLACE_ACT_FLAG, bd);
        startActivity(intent);
    }

    @Override
    public void goToLiveStreamAct(int accId) {
        this.accId = accId;
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    new String[]{Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO},
                    Constant.MY_PERMISSIONS_REQUEST_ACCESS_CAMERA);

            return;
        }

        Intent intent = new Intent(getActivity(), LiveVideoBroadcasterActivity.class);
        Bundle bd = new Bundle();
        bd.putInt("accId", accId);
        intent.putExtra(LiveVideoBroadcasterActivity.LIVE_STREAM_ACT_FLAG, bd);
        startActivity(intent);
    }

    @Override
    public void connectSocket() {
        this.iSocketConnect.onSocketConnect();
    }

    @Override
    public void goToFriendListAct(int accId) {
        Intent intent = new Intent(getActivity(), FriendListActivity.class);
        Bundle bd = new Bundle();
        bd.putInt("accId", accId);
        intent.putExtra(FriendListActivity.FRIEND_LIST_ACT_FLAG, bd);
        startActivity(intent);
    }

    @Override
    public void goToVodAct(int accId,String accName) {
        Intent intent = new Intent(getActivity(), VodActivity.class);
        Bundle bd = new Bundle();
        bd.putInt("accId", accId);
        bd.putString("accName",accName);
        intent.putExtra(VodActivity.FRIEND_VOD_ACT_FLAG, bd);
        startActivity(intent);
    }

    @Override
    public void onFriendItemClick(int friendId) {
        SharedPreferences prefs = TravelSharedPreference.getINSTANCE();
        if (prefs.getBoolean(Constant.SHARED_LOGIN_STATE, false) == true) {
            if (friendId != prefs.getInt(Constant.SHARED_LOGIN_ACC_ID, -1)) {
                goToFriendAct(friendId);
            } else {
                String accType = prefs.getString(Constant.SHARED_LOGIN_TYPE, "");
                goToProfileAct(friendId, accType);
            }
        } else {
            goToFriendAct(friendId);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constant.MY_PERMISSIONS_REQUEST_ACCESS_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "Permission was granted!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(), LiveVideoBroadcasterActivity.class);
                    Bundle bd = new Bundle();
                    bd.putInt("accId", this.accId);
                    intent.putExtra(LiveVideoBroadcasterActivity.LIVE_STREAM_ACT_FLAG, bd);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "Permission denied!", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    public interface ISocketConnect {
        void onSocketConnect();
    }
}
