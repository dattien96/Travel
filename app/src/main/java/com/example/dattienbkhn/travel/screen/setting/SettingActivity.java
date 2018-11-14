package com.example.dattienbkhn.travel.screen.setting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.dattienbkhn.travel.R;
import com.example.dattienbkhn.travel.database.sharedPreference.TravelSharedPreference;
import com.example.dattienbkhn.travel.databinding.ActivitySettingBinding;
import com.example.dattienbkhn.travel.screen.profile.ProfileActivity;
import com.example.dattienbkhn.travel.screen.stream.SocketConfig;
import com.example.dattienbkhn.travel.utils.Constant;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import static com.example.dattienbkhn.travel.screen.profile.ProfileActivity.PROFILE_ACT_FLAG;

public class SettingActivity extends AppCompatActivity implements SettingContract.View {
    private ActivitySettingBinding binding;
    private SettingViewModel mViewModel;

    //google
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions gso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting);
        mViewModel = new SettingViewModel();
        mViewModel.setViewNavigator(this);
        mViewModel.setBinding(binding);
        onInitViewModel();
        binding.setViewModel(mViewModel);

        //google
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    public void onInitViewModel() {
        mViewModel.onStart();
    }

    @Override
    public void onErrorMessage(String mes) {
        Toast.makeText(this, "" + mes, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goFinish() {
        finish();
    }

    @Override
    public void googleLogOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //save login state
                        SharedPreferences prefs = TravelSharedPreference.getINSTANCE();
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean(Constant.SHARED_LOGIN_STATE, false);
                        editor.putString(Constant.SHARED_LOGIN_TYPE, Constant.SHARED_LOGIN_FACEBOOK);
                        editor.putInt(Constant.SHARED_LOGIN_ACC_ID, -1);
                        editor.commit();
                        SocketConfig.getSocketInstance().disconnect();
                        goFinish();
                    }
                });
    }

    @Override
    public void appAccLogOut() {

    }

    @Override
    public void goToProfileAct(int accId, String accType) {
        Intent intent = new Intent(this, ProfileActivity.class);
        Bundle bd = new Bundle();
        bd.putInt("accId",accId);
        bd.putString("accType",accType);
        intent.putExtra(PROFILE_ACT_FLAG,bd);
        startActivity(intent);
    }
}
