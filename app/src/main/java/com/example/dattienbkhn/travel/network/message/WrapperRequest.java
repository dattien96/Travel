package com.example.dattienbkhn.travel.network.message;

/**
 * Created by dattienbkhn on 02/02/2018.
 */

public class WrapperRequest<T> {
    private T data;
    private String message;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
