package com.example.dattienbkhn.travel.screen.friend;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.dattienbkhn.travel.R;
import com.example.dattienbkhn.travel.databinding.ActivityFriendBinding;
import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.screen.detailsplace.PlaceActivity;
import com.example.dattienbkhn.travel.screen.vod.VodActivity;
import com.example.dattienbkhn.travel.screen.lovedplace.LovedPlaceAdapter;
import com.example.dattienbkhn.travel.screen.main.mainfragment.ItemFamousPlaceCountryViewModel;
import com.example.dattienbkhn.travel.screen.stream.SocketConfig;
import com.example.dattienbkhn.travel.screen.stream.playstream.LiveVideoPlayerActivity;
import com.example.dattienbkhn.travel.utils.Constant;
import com.example.dattienbkhn.travel.utils.dialog.DialogManager;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FriendActivity extends AppCompatActivity implements FriendContract.View, ItemFamousPlaceCountryViewModel.IItemPlaceClick {
    public static final String FRIEND_ACT_FLAG = "FriendActivity";

    private ActivityFriendBinding binding;
    private FriendViewModel mViewModel;
    private DialogManager mDialogManager;
    private int mCurFriendId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_friend);

        mCurFriendId = getIntent().getIntExtra(FRIEND_ACT_FLAG,-1);

        if (SocketConfig.getSocketInstance()  != null) {
            SocketConfig.getSocketInstance().on("new_stream", onNewMessage);
        }

        mDialogManager = new DialogManager(this);
        mViewModel = new FriendViewModel(mDialogManager);
        mViewModel.setViewNavigator(this);
        mViewModel.setCurFriendId(mCurFriendId);
        mViewModel.setBinding(binding);
        mViewModel.setLovedPlaceAdapter(new LovedPlaceAdapter(this, new ArrayList<Place>(), this));

        onInitViewModel();
        binding.setViewModel(mViewModel);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewModel.onStop();
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
    public void onItemPlaceClick(Place obj) {
        goToPlaceDetailsAct(obj);
    }

    @Override
    public void goToPlaceDetailsAct(Place obj) {
        Intent intent = new Intent(this, PlaceActivity.class);
        Bundle bd = new Bundle();
        bd.putSerializable("place", obj);
        intent.putExtra(PlaceActivity.PLACE_ACT_FLAG, bd);
        startActivity(intent);
    }

    @Override
    public void goToPlayStreamAct(int friendId) {
        Intent intent = new Intent(this, LiveVideoPlayerActivity.class);
        Bundle bd = new Bundle();
        bd.putInt("streamType", Constant.STREAM_TYPE_LIVE_RTMP);
        bd.putInt("accId", friendId);
        intent.putExtra(LiveVideoPlayerActivity.LIVE_PLAYER_ACT_FLAG, bd);
        startActivity(intent);
    }

    @Override
    public void goToVodAct(int accId,String accName) {
        Intent intent = new Intent(this, VodActivity.class);
        Bundle bd = new Bundle();
        bd.putInt("accId", accId);
        bd.putString("accName",accName);
        intent.putExtra(VodActivity.FRIEND_VOD_ACT_FLAG, bd);
        startActivity(intent);
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {

        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    int accId;
                    int streamState;
                    try {
                        accId = data.getInt("accId");
                        if (mCurFriendId == accId) {
                            streamState = data.getInt("streamState");
                            boolean bStreamState = (streamState == 1) ?true :false;
                            mViewModel.updateStreamState(bStreamState);
                        }
                    } catch (JSONException e) {
                        return;
                    }

                }
            });
        }
    };
}
