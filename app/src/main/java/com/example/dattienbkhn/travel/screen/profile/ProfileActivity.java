package com.example.dattienbkhn.travel.screen.profile;

import android.app.AlertDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.dattienbkhn.travel.R;
import com.example.dattienbkhn.travel.databinding.ActivityProfileBinding;
import com.example.dattienbkhn.travel.utils.dialog.DialogManager;
import com.google.common.io.ByteStreams;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ProfileActivity extends AppCompatActivity implements ProfileContract.View {
    public static final String PROFILE_ACT_FLAG = "ProfileActivity";
    public static final int PICK_PHOTO_FOR_AVATAR = 1;

    private DialogManager mDialogManager;
    private ActivityProfileBinding binding;
    private ProfileViewModel mViewModel;
    private AlertDialog.Builder builder;

    //this property = true if viewmodel call pickImage for avatar
    //false if viewmodel call pickImage for cover img
    private boolean isAvatar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_profile);

        Bundle bd = getIntent().getExtras().getBundle(PROFILE_ACT_FLAG);
        int accId = bd.getInt("accId");
        String accType = bd.getString("accType");

        mDialogManager = new DialogManager(this);
        builder = new AlertDialog.Builder(this);
        mViewModel = new ProfileViewModel(mDialogManager,this);
        mViewModel.setBinding(binding);
        mViewModel.setViewNavigator(this);
        mViewModel.setBuilder(builder);
        mViewModel.setAccId(accId);
        mViewModel.setAccType(accType);

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
    public void pickImage(boolean isAvatar) {
        this.isAvatar = isAvatar;
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                InputStream inputStream = null;
                try {
                    inputStream = this.getContentResolver().openInputStream(resultUri);

                    /*BufferedInputStream bis = new BufferedInputStream(inputStream);
                    Bitmap bm = BitmapFactory.decodeStream(bis);
                    int PHOTO_WIDTH = 320;
                    int PHOTO_HEIGHT = 240;
                    Bitmap resized = Bitmap.createScaledBitmap(bm, PHOTO_WIDTH, PHOTO_HEIGHT, true);
                    ByteArrayOutputStream blob = new ByteArrayOutputStream();
                    resized.compress(Bitmap.CompressFormat.PNG, 50, blob);
                    mViewModel.setProfileImage(blob.toByteArray(),isAvatar);
                    resized.recycle();*/

                    byte[] bytes = ByteStreams.toByteArray(inputStream);
                    mViewModel.setProfileImage(bytes,isAvatar);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();

            }
        }
    }

    /*@Override
    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image*//**//*");
        startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO_FOR_AVATAR) {
            if (data == null) {
                //Display an error
                return;
            }
            try {
                InputStream inputStream = this.getContentResolver().openInputStream(data.getData());
                BufferedInputStream bis = new BufferedInputStream(inputStream);
                Bitmap bm = BitmapFactory.decodeStream(bis);

                binding.imgCover.setImageBitmap(bm);
                binding.imgAvatar.setImageBitmap(bm);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
            //Now you can do whatever you want with your inpustream, save it as file, upload to a server, decode a bitmap...
    }*/
}
