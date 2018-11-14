package com.example.dattienbkhn.travel.utils.executor;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by DatTien on 1/14/2018.
 */

public class IoThreadExecutor implements Executor {
    private final Executor mDiskIO;

    public IoThreadExecutor() {
        mDiskIO = Executors.newSingleThreadExecutor();
    }
    @Override
    public void execute(@NonNull Runnable runnable) {
        //runnable.run();
        mDiskIO.execute(runnable);
    }
}
