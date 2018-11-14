package com.example.dattienbkhn.travel.screen.camera;

import java.io.Serializable;

/**
 * Created by DatTien on 7/5/2017.
 */

public class ImageCameraObj implements Serializable {
    private byte[] bImageRes;   //data src cua anh
    private boolean longClickCheck;  //bien check cho su kien longClick o adapter

    public ImageCameraObj(byte[] bImageRes, boolean longClickCheck) {
        this.bImageRes = bImageRes;
        this.longClickCheck = longClickCheck;
    }

    public byte[] getbImageRes() {
        return bImageRes;
    }

    public void setbImageRes(byte[] bImageRes) {
        this.bImageRes = bImageRes;
    }

    public boolean isLongClickCheck() {
        return longClickCheck;
    }

    public void setLongClickCheck(boolean longClickCheck) {
        this.longClickCheck = longClickCheck;
    }
}
