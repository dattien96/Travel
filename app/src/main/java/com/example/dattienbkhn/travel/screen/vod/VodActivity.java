package com.example.dattienbkhn.travel.screen.vod;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.dattienbkhn.travel.R;
import com.example.dattienbkhn.travel.databinding.ActivityVodBinding;
import com.example.dattienbkhn.travel.screen.stream.playstream.LiveVideoPlayerActivity;
import com.example.dattienbkhn.travel.utils.Constant;
import com.example.dattienbkhn.travel.utils.dialog.DialogManager;

import java.util.ArrayList;

public class VodActivity extends AppCompatActivity implements VodContract.View,
        ItemVodViewModel.IVodItemCLick {
    public static final String FRIEND_VOD_ACT_FLAG = "FriendVodActivity";
    private int mCurAccId;
    private String mCurAccName;
    private ActivityVodBinding binding;
    private VodViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_vod);

        Bundle bd = getIntent().getExtras().getBundle(FRIEND_VOD_ACT_FLAG);
        mCurAccId = bd.getInt("accId", -1);
        mCurAccName = bd.getString("accName", "");

        mViewModel = new VodViewModel(new DialogManager(this));
        mViewModel.setViewNavigator(this);
        mViewModel.setCurAccId(mCurAccId);
        mViewModel.setCurName(mCurAccName);
        mViewModel.setVodAdapter(new VodAdapter(this, new ArrayList<String>(), this));

        onInitViewModel();
        binding.setViewModel(mViewModel);
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
    public void finishView() {
        finish();
    }

    @Override
    public void onVodClick(String vodName) {
        if (vodName != null && vodName.length() > 0) {
            Intent intent = new Intent(this, LiveVideoPlayerActivity.class);
            Bundle bd = new Bundle();
            bd.putInt("streamType", Constant.STREAM_TYPE_VOD_RTMP);
            bd.putInt("accId", mCurAccId);
            bd.putString("vodName",vodName);
            intent.putExtra(LiveVideoPlayerActivity.LIVE_PLAYER_ACT_FLAG, bd);
            startActivity(intent);
        } else {
            onErrorMessage("Can not play this video!");
        }
    }
}
