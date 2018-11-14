package com.example.dattienbkhn.travel.model.socketmes;

/**
 * Created by tiendatbkhn on 21/04/2018.
 */

public class StreamState {
    private int accId;
    private int streamState;

    public StreamState(int accId, int streamState) {
        this.accId = accId;
        this.streamState = streamState;
    }

    public int getAccId() {
        return accId;
    }

    public void setAccId(int accId) {
        this.accId = accId;
    }

    public int getStreamState() {
        return streamState;
    }

    public void setStreamState(int streamState) {
        this.streamState = streamState;
    }
}
