package com.example.dattienbkhn.travel.screen.comment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.dattienbkhn.travel.R;
import com.example.dattienbkhn.travel.database.sharedPreference.TravelSharedPreference;
import com.example.dattienbkhn.travel.databinding.ActivityCommentBinding;
import com.example.dattienbkhn.travel.model.app.Comment;
import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.screen.detailsplace.CommentAdapter;
import com.example.dattienbkhn.travel.screen.detailsplace.ItemCommentViewModel;
import com.example.dattienbkhn.travel.screen.friend.FriendActivity;
import com.example.dattienbkhn.travel.screen.profile.ProfileActivity;
import com.example.dattienbkhn.travel.utils.Constant;
import com.example.dattienbkhn.travel.utils.dialog.DialogManager;

import java.util.ArrayList;

import static com.example.dattienbkhn.travel.screen.profile.ProfileActivity.PROFILE_ACT_FLAG;

public class CommentActivity extends AppCompatActivity implements CommentContract.View,ItemCommentViewModel.IItemUserClick {
    public static final String CMT_ACT_FLAG = "CommentActivity";

    private CommentViewModel mViewModel;
    private DialogManager mDialogManager;
    private CommentAdapter mAdapter;
    private int accId;
    private Place place;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCommentBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_comment);

        Bundle bd = getIntent().getExtras().getBundle(CMT_ACT_FLAG);
        accId = bd.getInt("accId");
        place = (Place) bd.getSerializable("place");

        mDialogManager = new DialogManager(this);
        mAdapter = new CommentAdapter(this,new ArrayList<Comment>(),this);

        mViewModel = new CommentViewModel(mDialogManager);
        mViewModel.setViewNavigator(this);
        mViewModel.setActivityCommentBinding(binding);
        mViewModel.setCommentAdapter(mAdapter);
        mViewModel.setAccId(accId);
        mViewModel.setPlace(place);

        onInitViewModel();
        binding.setViewModel(mViewModel);
    }

    @Override
    public void onInitViewModel() {
        mViewModel.onStart();
    }

    @Override
    public void onErrorMessage(String mes) {
        Toast.makeText(this, ""+mes, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goFinish() {
        finish();
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
        bd.putInt("accId",accId);
        bd.putString("accType",accType);
        intent.putExtra(PROFILE_ACT_FLAG,bd);
        startActivity(intent);
    }

    @Override
    public void onUserItemClick(int accId) {
        SharedPreferences prefs = TravelSharedPreference.getINSTANCE();
        if (prefs.getBoolean(Constant.SHARED_LOGIN_STATE, false) == true) {
            if (accId != prefs.getInt(Constant.SHARED_LOGIN_ACC_ID, -1)) {
                goToFriendAct(accId);
            } else {
                String accType = prefs.getString(Constant.SHARED_LOGIN_TYPE,"");
                goToProfileAct(accId,accType);
            }
        } else {
            goToFriendAct(accId);
        }
    }
}
