package com.example.dattienbkhn.travel.network.message.image;

/**
 * Created by dattienbkhn on 10/02/2018.
 */

public class ImageResponse<T> {
    private T data;
    private String msg;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
