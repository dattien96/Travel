package com.example.dattienbkhn.travel.screen.camera;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.example.dattienbkhn.travel.R;

import java.util.List;


/**
 * Created by DatTien on 19/01/2017.
 */

public class AdapterGridImage extends RecyclerView.Adapter<AdapterGridImage.ImageCameraHolder> {
    public static List<ImageCameraObj> listImageCameraObj;
    Activity ctx;
    private IimageCameraListener iimageCameraListener;
    private int numOfLOngClick;//voi moi mot anh deu co 1 check longClick rieng
    //con check nay la check cho toan bo recycleView <bang 0 la k co anh dc longC>
    // <neu co 1 anh dang duoc longCLick thi k cho click binh thuong nua>

    public AdapterGridImage(List<ImageCameraObj> lis, Activity ctx, IimageCameraListener iimageCameraListener) {
        this.listImageCameraObj = lis;
        this.ctx = ctx;
        this.iimageCameraListener = iimageCameraListener;
        numOfLOngClick = 0; //ban dau chua co anh nao duoc longClcik thi de la false
    }

    @Override
    public ImageCameraHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View convertView = ctx.getLayoutInflater().inflate(R.layout.lt_grid_layout, parent, false);
        return new ImageCameraHolder(convertView);
    }

    @Override
    public void onBindViewHolder(final ImageCameraHolder holder, final int position) {

        final ImageCameraObj obj = listImageCameraObj.get(position);
        Bitmap bitMap = BitmapFactory.decodeByteArray(obj.getbImageRes(), 0, obj.getbImageRes().length);
        holder.imgOfCamera.setImageBitmap(bitMap);
        holder.imgOfCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!obj.isLongClickCheck() && numOfLOngClick == 0) //neu anh k longC va ngoai ra cung k co anh khac longC thi moi thuc hien
                    iimageCameraListener.imageClickListener(position);
                if (obj.isLongClickCheck()) { //neu longClick roi ma click tiep thi bo longClick cho anh do
                    numOfLOngClick--;
                    obj.setLongClickCheck(false);
                    holder.layoutBgOfImage.setVisibility(View.GONE); //huy bo backGround xung quanh anh
                    iimageCameraListener.imageRemoveLongCLickListener(position);
                }

            }
        });
        holder.imgOfCamera.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (!obj.isLongClickCheck()) { //neu anh chua duoc longClick thi se thuc hien chon
                    numOfLOngClick++;
                    obj.setLongClickCheck(true);
                    holder.layoutBgOfImage.setVisibility(View.VISIBLE);
                    iimageCameraListener.imageLongClickListener(listImageCameraObj.get(position));
                }
                //con neu da longclick roi thi k lamf j nua

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return listImageCameraObj.size();
    }

    public class ImageCameraHolder extends RecyclerView.ViewHolder {
        private ImageView imgOfCamera;
        private RelativeLayout ltContainOfImageCamera;
        private RelativeLayout layoutBgOfImage;

        public ImageCameraHolder(View itemView) {
            super(itemView);
            imgOfCamera = (ImageView) itemView.findViewById(R.id.itemGridImg);
            ltContainOfImageCamera = (RelativeLayout) itemView.findViewById(R.id.ltContainOfImageCamera);
            layoutBgOfImage = (RelativeLayout) itemView.findViewById(R.id.layoutBgOfImage);

        }
    }

    public interface IimageCameraListener {
        void imageClickListener(int position); //su kien click share 1 anh

        void imageLongClickListener(ImageCameraObj imageCameraObj);   //su kien chon nhieu anh

        void imageRemoveLongCLickListener(int postion);  //su kien bo longClick 1 anh
    }
}