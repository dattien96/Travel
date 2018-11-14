package com.example.dattienbkhn.travel.screen.main;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.dattienbkhn.travel.R;
import com.example.dattienbkhn.travel.database.sharedPreference.TravelSharedPreference;
import com.example.dattienbkhn.travel.databinding.ActivityMainBinding;
import com.example.dattienbkhn.travel.screen.friend.FriendActivity;
import com.example.dattienbkhn.travel.screen.main.favoritefragment.FavoriteFragment;
import com.example.dattienbkhn.travel.screen.main.mainfragment.MainFragment;
import com.example.dattienbkhn.travel.screen.main.mefragment.MeFragment;
import com.example.dattienbkhn.travel.screen.stream.SocketConfig;
import com.example.dattienbkhn.travel.utils.Constant;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements MainContract.View,MeFragment.ISocketConnect {

    private MainViewModel mMainViewModel;
    private ActivityMainBinding mMainBinding;
    private Socket mSocket;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        connectSocketIO();

        mMainViewModel = new MainViewModel();
        mMainViewModel.setViewNavigator(this);
        mMainBinding.setViewModel(mMainViewModel);
        onInitViewModel();
        findOrCreateMainFragment();
    }

    @NonNull
    private void findOrCreateMainFragment() {

       /* //if current frg is null or it is not the fragment that we need
        if (!(getSupportFragmentManager().findFragmentById(R.id.contentFrame)
                instanceof MainFragment) ||
                getSupportFragmentManager().findFragmentById(R.id.contentFrame) == null) {
            MainFragment frg = null;
            // we create new instance of frg that we need if it is not exist
            if (mainFragment == null) frg = new MainFragment();
            else frg = mainFragment;

            //if act has frg before,we remove it
            if (getSupportFragmentManager().findFragmentById(R.id.contentFrame) != null)
                getSupportFragmentManager().beginTransaction()
                        .remove(getSupportFragmentManager().findFragmentById(R.id.contentFrame))
                        .commit();

            //then inject my frg
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), frg, R.id.contentFrame);
        }*/

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (fragmentManager.findFragmentByTag(MainFragment.MAIN_FRAGMENT_TAG) == null) {
            Log.e("frg","MainFrg chay lan dau");
            transaction.add(R.id.contentFrame, new MainFragment(),MainFragment.MAIN_FRAGMENT_TAG);
            transaction.addToBackStack(MainFragment.MAIN_FRAGMENT_TAG);
            transaction.commit();
        }else {
            Log.e("frg","MainFrg lay tu stack , stack co "+fragmentManager.getBackStackEntryCount());
            transaction.replace(R.id.contentFrame,fragmentManager.findFragmentByTag(MainFragment.MAIN_FRAGMENT_TAG));
            transaction.commit();
        }

    }

    @NonNull
    private void findOrCreateFavoriteFragment() {

        /*//if current frg is null or it is not the fragment that we need
        if (!(getSupportFragmentManager().findFragmentById(R.id.contentFrame)
                instanceof FavoriteFragment) ||
                getSupportFragmentManager().findFragmentById(R.id.contentFrame) == null) {
            FavoriteFragment frg = null;
            // we create new instance of frg that we need if it is not exist
            if (favoriteFragment == null) frg = new FavoriteFragment();
            else frg = favoriteFragment;

            //if act has frg before,we remove it
            if (getSupportFragmentManager().findFragmentById(R.id.contentFrame) != null)
                getSupportFragmentManager().beginTransaction()
                        .remove(getSupportFragmentManager().findFragmentById(R.id.contentFrame))
                        .commit();

            //then inject my frg
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), frg, R.id.contentFrame);
        }*/

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (fragmentManager.findFragmentByTag(FavoriteFragment.FAVORITE_FRAGMENT_TAG) == null) {
            Log.e("frg","FavoriteFrg chay lan dau");
            transaction.add(R.id.contentFrame, new FavoriteFragment(),FavoriteFragment.FAVORITE_FRAGMENT_TAG);
            transaction.addToBackStack(FavoriteFragment.FAVORITE_FRAGMENT_TAG);
            transaction.commit();
        }else {
            Log.e("frg","FavoriteFrg lay tu stack , stack co "+fragmentManager.getBackStackEntryCount());
            transaction.replace(R.id.contentFrame,fragmentManager.findFragmentByTag(FavoriteFragment.FAVORITE_FRAGMENT_TAG));
            transaction.commit();
        }

    }

    @NonNull
    private void findOrCreateMeFragment() {

        /*//if current frg is null or it is not the fragment that we need
        if (!(getSupportFragmentManager().findFragmentById(R.id.contentFrame)
                instanceof MeFragment) ||
                getSupportFragmentManager().findFragmentById(R.id.contentFrame) == null) {
            MeFragment frg = null;
            // we create new instance of frg that we need if it is not exist
            if (meFragment == null) {
                frg = new MeFragment();
                frg.setSocketConnect(this);
            }
            else frg = meFragment;

            //if act has frg before,we remove it
            if (getSupportFragmentManager().findFragmentById(R.id.contentFrame) != null)
                getSupportFragmentManager().beginTransaction()
                        .remove(getSupportFragmentManager().findFragmentById(R.id.contentFrame))
                        .commit();

            //then inject my frg
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), frg, R.id.contentFrame);
        }*/

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

            Log.e("frg","MeFrg chay lan dau");
            MeFragment frg = new MeFragment();
            frg.setSocketConnect(this);
            transaction.add(R.id.contentFrame, frg ,MeFragment.ME_FRAGMENT_TAG);
            transaction.commit();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroySocketIO();
    }

    @Override
    public void onInitViewModel() {
        mMainViewModel.onStart();
    }

    @Override
    public void onErrorMessage(String mes) {
        Toast.makeText(this, "" + mes, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void showHomeFrg() {
         findOrCreateMainFragment();
    }

    @Override
    public void showMeFrg() {
         findOrCreateMeFragment();
    }

    @Override
    public void showFavoriteFrg() {
         findOrCreateFavoriteFragment();
    }

    @Override
    public void connectSocketIO() {
        SharedPreferences prefs = TravelSharedPreference.getINSTANCE();
        if (prefs.getBoolean(Constant.SHARED_LOGIN_STATE, false)) {

            mSocket = SocketConfig.getSocketInstance();
            mSocket.connect();

            mSocket.emit("new_acc", prefs.getInt(Constant.SHARED_LOGIN_ACC_ID,-1));
            mSocket.on("new_stream", onNewMessage);
        }
    }

    @Override
    public void destroySocketIO() {
        if (mSocket != null && mSocket.connected()) {
            mSocket.disconnect();
            mSocket.off("new_stream", onNewMessage);
        }
    }

    @Override
    public void showNotificationStream(int accId,String accName) {
        Intent intent = new Intent(getApplicationContext(), FriendActivity.class);
        /*Bundle bd = new Bundle();
        bd.putInt("accId", accId);*/
        intent.putExtra(FriendActivity.FRIEND_ACT_FLAG, accId);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "xxx")
                .setSmallIcon(R.drawable.online)
                .setContentTitle("Live Stream")
                .setContentText(accName+ " is streaming!")
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(PendingIntent.getActivity(this, accId, intent, 0));

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(accId, mBuilder.build());
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
                        streamState = data.getInt("streamState");
                        mMainViewModel.notifyNewStream(accId,streamState);

                    } catch (JSONException e) {
                        return;
                    }

                }
            });
        }
    };

    @Override
    public void onSocketConnect() {
        connectSocketIO();
    }

}
