package com.example.dattienbkhn.travel.screen.friendlist;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.dattienbkhn.travel.R;
import com.example.dattienbkhn.travel.databinding.ActivityFriendListBinding;
import com.example.dattienbkhn.travel.model.app.Friend;
import com.example.dattienbkhn.travel.network.message.user.UserSearch;
import com.example.dattienbkhn.travel.screen.friend.FriendActivity;
import com.example.dattienbkhn.travel.screen.main.mefragment.FriendAdapter;
import com.example.dattienbkhn.travel.screen.main.mefragment.ItemFriendViewModel;
import com.example.dattienbkhn.travel.utils.dialog.DialogManager;

import java.util.ArrayList;

public class FriendListActivity extends AppCompatActivity implements FriendListContract.View,ItemFriendViewModel.IItemFriendClick {
    public static final String FRIEND_LIST_ACT_FLAG = "FriendListActivity";
    private int mCurAccId;
    private ActivityFriendListBinding binding;
    private FriendListViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_friend_list);

        Bundle bd = getIntent().getExtras().getBundle(FriendListActivity.FRIEND_LIST_ACT_FLAG);
        mCurAccId = bd.getInt("accId",-1);
        mViewModel = new FriendListViewModel(new DialogManager(this));
        mViewModel.setViewNavigator(this);
        mViewModel.setCurAccId(mCurAccId);
        mViewModel.setAdapter(new FriendAdapter(this,new ArrayList<Friend>(),this));
        mViewModel.setSearchAdapter(new FriendsSearchAdapter(this,new ArrayList<UserSearch>(),this));
        mViewModel.setBinding(binding);

        onInitViewModel();
        binding.setViewModel(mViewModel);

    }

    @Override
    protected void onPause() {
        super.onPause();
        mViewModel.onHideResultSearch();
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
    public void onFriendItemClick(int friendId) {
        goToFriendAct(friendId);
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
    public void finishView() {
        finish();
    }
}
