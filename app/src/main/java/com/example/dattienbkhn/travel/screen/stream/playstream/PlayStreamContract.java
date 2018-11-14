package com.example.dattienbkhn.travel.screen.stream.playstream;

/**
 * Created by tiendatbkhn on 21/04/2018.
 */

public interface PlayStreamContract {
    void onStreamEnd();

    void showConfirmDialog();

    void getStreamState(int friendId);
}
