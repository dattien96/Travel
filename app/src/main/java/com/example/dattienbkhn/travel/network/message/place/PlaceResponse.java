package com.example.dattienbkhn.travel.network.message.place;

/**
 * Created by dattienbkhn on 07/02/2018.
 */

public class PlaceResponse<T> {
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
