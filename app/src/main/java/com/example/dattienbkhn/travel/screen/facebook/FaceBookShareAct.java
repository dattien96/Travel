package com.example.dattienbkhn.travel.screen.facebook;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;


import com.example.dattienbkhn.travel.R;


public class FaceBookShareAct extends AppCompatActivity {
    ImageView imgOfCamera;
    Bitmap imgBitMap;
    private boolean actStateCheck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fb_share_act);
        imgOfCamera = findViewById(R.id.imgOfCamera);
        getData();


    }
    private void getData() {
        Bundle bd = getIntent().getExtras().getBundle("imageByteFromCameraAct");
        if (bd != null) { //da nhan data tu ben camera
            byte[] imageByte = bd.getByteArray("imageByte");
            imgBitMap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
            imgOfCamera.setImageBitmap(imgBitMap);
            actStateCheck = true; //the hien du lieu share anh
        } else { //neu bd ben camera null thi act nay duoc goi tu audioDialog de share link add
            Bundle bd1 = getIntent().getExtras().getBundle("dataFromAudioDialogToFbShare");
            actStateCheck = false; //the hien du lieu share link

        }
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
     //   callbackManager.onActivityResult(requestCode, resultCode, data);
    }



}
