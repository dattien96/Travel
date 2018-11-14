package com.example.dattienbkhn.travel.utils.navigator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

public class Navigator {

    @NonNull
    private FragmentActivity mActivity;
    private Fragment mFragment;

    public Navigator(@NonNull FragmentActivity activity) {
        mActivity = activity;
    }

    public Navigator(Fragment fragment) {
        mFragment = fragment;
        mActivity = fragment.getActivity();
    }

    public void startActivity(@NonNull Intent intent) {
        mActivity.startActivity(intent);
    }

    public void startActivity(@NonNull Class<? extends Activity> clazz) {
        startActivity(new Intent(mActivity, clazz));
    }

    public void startActivity(@NonNull Class<? extends Activity> clazz, Bundle args) {
        Intent intent = new Intent(mActivity, clazz);
        intent.putExtras(args);
        startActivity(intent);
    }

    public void startActivityForResult(@NonNull Intent intent, int requestCode) {
        mActivity.startActivityForResult(intent, requestCode);
    }

    public void startActivityForResult(@NonNull Class<? extends Activity> clazz, int requestCode) {
        startActivityForResult(new Intent(mActivity, clazz), requestCode);
    }

    public void startActivityForResult(@NonNull Class<? extends Activity> clazz, Bundle args, int requestCode) {
        Intent intent = new Intent(mActivity, clazz);
        intent.putExtras(args);
        startActivityForResult(intent, requestCode);
    }

    public void finishActivity() {
        if (mActivity != null) {
            mActivity.finish();
        }
        if (mFragment != null) {
            mFragment.getActivity().finish();
        }
    }

    public void finishActivity(int resultCode) {
        if (mActivity != null) {
            mActivity.setResult(resultCode);
            mActivity.finish();
        }
        if (mFragment != null) {
            mFragment.getActivity().setResult(resultCode);
            mFragment.getActivity().finish();
        }
    }

    public void finishActivity(int resultCode, Intent intent) {
        if (mActivity != null) {
            mActivity.setResult(resultCode, intent);
            mActivity.finish();
        }
        if (mFragment != null) {
            mFragment.getActivity().setResult(resultCode, intent);
            mFragment.getActivity().finish();
        }
    }



    public void showToastCenterText(String message) {
        Toast toast = Toast.makeText(mActivity, message, Toast.LENGTH_SHORT);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        if( v != null) v.setGravity(Gravity.CENTER);
        toast.show();
    }

    public void onBackPressed() {
        mActivity.onBackPressed();
    }
}
