package com.example.dattienbkhn.travel.screen.stream;

import com.example.dattienbkhn.travel.utils.Constant;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

/**
 * Created by tiendatbkhn on 22/04/2018.
 */

public class SocketConfig {
    private static Socket mSocket;

    public static Socket getSocketInstance() {
        if (mSocket == null) {
            try {
                mSocket = IO.socket(Constant.SOCKET_IO_URL);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }

        return mSocket;
    }
}
