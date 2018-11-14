package com.example.dattienbkhn.travel.screen.camera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.dattienbkhn.travel.R;
import com.example.dattienbkhn.travel.screen.facebook.FaceBookShareAct;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by DatTien on 3/31/2017.
 */

public class CameraActivity extends Activity implements AdapterGridImage.IimageCameraListener {
    private Camera mCamera;
    private CameraPreview mPreview;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private boolean checkTakePicture = true;
    private RecyclerView gridListImageCamera;
    private ImageView imgTakePicture;
    private ImageView imgShowListImageOfCamera;
    private List<ImageCameraObj> listImageFromCamera;
    private AdapterGridImage gridImageAdapter;
    private LinearLayout layoutOfListImage;
    private FrameLayout camera_view;
    private RelativeLayout layoutOfLongClick;
    private ImageView imgShareOfLongClickImage;
    private TextView txtNumberOfSelectImg;
    private List<ImageCameraObj> listImageOfLongClick;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listImageFromCamera = new ArrayList<>();
        listImageOfLongClick = new ArrayList<>();
    }


    //callBack sau khi anh duoc chujp thanh cong
    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            Bitmap bitMap = BitmapFactory.decodeByteArray(data, 0, data.length);
            checkTakePicture = true;
            mCamera.startPreview();
            ImageCameraObj imageCameraObj = new ImageCameraObj(data, false); //tao ra obj anh voi check longClick bang false
            imgShowListImageOfCamera.setImageBitmap(bitMap);
            imgShowListImageOfCamera.setVisibility(View.VISIBLE);
            listImageFromCamera.add(imageCameraObj);
            gridImageAdapter.notifyDataSetChanged();
            gridListImageCamera.smoothScrollToPosition(listImageFromCamera.size() - 1); //luon move view den anh cuoi cung

            // layoutOfListImage.setVisibility(View.VISIBLE); //sau khi chup xong hien thi gridView
            try {
                String state = Environment.getExternalStorageState();
                if (Environment.MEDIA_MOUNTED.equals(state)) {
                    saveImageToExternalStorage(bitMap);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.camera_act);
        addControls();
        addEvents();
    }

    private void addEvents() {
        imgTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkTakePicture) {
                    mCamera.takePicture(null, null, mPicture);
                    checkTakePicture = false;
                    //sau khi chụp xong gán lại bằng false để có ấn chụp tiếp cũng k chụp đc
                    //vì ngay lúc này preview() chưa đc khởi động lại nếu chụp tiếp sẽ lỗi
                    //trong pictureCallBack sẽ start lại preview và gán lại check = true
                }

            }
        });

        camera_view.setOnClickListener(new View.OnClickListener() { //sau khi chup anh,an tiep vao man hinh de chup tiep
            @Override
            public void onClick(View v) {

                layoutOfListImage.setVisibility(View.GONE);
                checkTakePicture = true;
                if (!(listImageOfLongClick.size() > 0)) //neu khong co anh nao duoc longclick thi khi an album nay se khong gone layout
                    layoutOfLongClick.setVisibility(View.GONE);


            }

        });
        imgShowListImageOfCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutOfListImage.setVisibility(View.VISIBLE);
                checkTakePicture = false;
            }
        });
        imgShareOfLongClickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CameraActivity.this, "Shared", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addControls() {
        cameraConfig();

        imgShareOfLongClickImage = findViewById(R.id.imgShareOfLongClickImage);
        txtNumberOfSelectImg = findViewById(R.id.txtNumberOfSelectImg);
        layoutOfLongClick = findViewById(R.id.layoutOfLongClick);
        layoutOfLongClick.setVisibility(View.GONE);
        imgShareOfLongClickImage = findViewById(R.id.imgShareOfLongClickImage);
        imgShowListImageOfCamera = findViewById(R.id.btnShowListImageOfCamera);
        imgShowListImageOfCamera.setVisibility(View.GONE);
        imgTakePicture = findViewById(R.id.img_capture);
        gridListImageCamera = findViewById(R.id.gridListImageCamera);
        layoutOfListImage = findViewById(R.id.layoutOfListImage);
        layoutOfListImage.setVisibility(View.GONE); //luc bat dau chay thi de an de chup anh
        gridImageAdapter = new AdapterGridImage(listImageFromCamera, CameraActivity.this, CameraActivity.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CameraActivity.this, LinearLayoutManager.HORIZONTAL, false);
        gridListImageCamera.setAdapter(gridImageAdapter);
        gridListImageCamera.setLayoutManager(layoutManager);
    }

    private void cameraConfig() {
        mCamera = Camera.open();


        Camera.Parameters parameters;
        parameters = mCamera.getParameters();
        mCamera.setDisplayOrientation(90);

        // Turn on the camera flash.
        String NowFlashMode = parameters.getFlashMode();
        if (NowFlashMode != null)
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        // Set the auto-focus.
        String NowFocusMode = parameters.getFocusMode();
        if (NowFocusMode != null)
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);

        mCamera.setParameters(parameters);
        mPreview = new CameraPreview(this, mCamera);//create a SurfaceView to show camera data
        camera_view = findViewById(R.id.camera_preview);
        camera_view.addView(mPreview);//add the SurfaceView to the layout

    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseMediaRecorder();       // if you are using MediaRecorder, release it first
        releaseCamera();              // release the camera immediately on pause event
    }

    private void releaseMediaRecorder() {
      /*  if (mMediaRecorder != null) {
            mMediaRecorder.reset();   // clear recorder configuration
            mMediaRecorder.release(); // release the recorder object
            mMediaRecorder = null;
            mCamera.lock();           // lock camera for later use
        }*/
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
    }


    //save image
    public final static String APP_PATH_SD_CARD = "/Pictures/ImicTravel";


    public boolean saveImageToExternalStorage(Bitmap image) { //save image to SD card
        //String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + APP_PATH_SD_CARD;
        //tao them thu muc rieng cho cac thanh pho
        try {
           // String cityPath = fullPath + "/test";
           // File dir = new File(cityPath);
            //if (!dir.exists()) {
               // dir.mkdirs();
            //} //tao thu muc chua anh voi ten thanh pho hien tai

            Random rd = new Random();

            OutputStream fOut = null;
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),  "bkTravel"); //doan nay tao ten cho tung anh trong thu muc CameraAPI
            file.createNewFile();
            fOut = new FileOutputStream(file);

            // 100 means no compression, the lower you go, the stronger the compression
            image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();

            MediaStore.Images.Media.insertImage(CameraActivity.this.getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());


            return true;

        } catch (Exception e) {
            Log.e("saveToExternalStorage()", e.getMessage());
            return false;
        }
    }

    public boolean saveImageToInternalStorage(Bitmap image) {

        try {
// Use the compress method on the Bitmap object to write image to
// the OutputStream
            FileOutputStream fos = CameraActivity.this.openFileOutput("desiredFilename.png", Context.MODE_PRIVATE);
//You can change the value of Context.MODE_PRIVATE to Context.MODE_WORLD_READABLE so that other applications will be able to access those files.
// Writing the bitmap to the output stream
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();

            return true;
        } catch (Exception e) {
            Log.e("saveToInternalStorage()", e.getMessage());
            return false;
        }
    }

    @Override
    public void imageClickListener(int position) {
        Intent intent = new Intent(CameraActivity.this, FaceBookShareAct.class);
        Bundle bd = new Bundle();

        bd.putByteArray("imageByte", listImageFromCamera.get(position).getbImageRes());
        intent.putExtra("imageByteFromCameraAct", bd);
        startActivity(intent);
    }

    @Override
    public void imageLongClickListener(ImageCameraObj imageCameraObj) {
        listImageOfLongClick.add(imageCameraObj);
        layoutOfLongClick.setVisibility(View.VISIBLE); //hien thi layout cho su kien long click
        txtNumberOfSelectImg.setText(listImageOfLongClick.size() + ""); //hien thi so anh duoc chon
        gridImageAdapter.notifyDataSetChanged();
    }

    @Override
    public void imageRemoveLongCLickListener(int postion) {
        /*  listImageFromCamera.get(postion) : lay ra anh bi bo longC tu list Ban Dau
            remove doi tuong do khoi ListLongClick
            giam textView anh duoc chon xuong 1

         */
        listImageOfLongClick.remove(listImageFromCamera.get(postion));
        txtNumberOfSelectImg.setText(listImageOfLongClick.size() + ""); //hien thi so anh duoc chon
        if (listImageOfLongClick.size() == 0) {
            layoutOfLongClick.setVisibility(View.GONE);
        }
    }

    //2 ham lay ra bitmap dung kich thuoc mong muon-->perfomance
    public static Bitmap decodeBitmapWithGiveSizeFromResource(Resources res, int resId,
                                                              int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        //neu kich thuoc lay ra cua anh lon hon kich thuoc mong doi thi giam di inSampleSize lan
        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight //cho den khi kich thuoc dau ra <= size mong muon thi tra ve so lan giamr size
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize; //gan int nay cho bien inSampleSize cua BitMapFactory.Options
    }
}
