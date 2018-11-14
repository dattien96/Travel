package com.example.dattienbkhn.travel.database.sharedPreference;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.dattienbkhn.travel.utils.Constant;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by dattienbkhn on 02/03/2018.
 */

public class TravelSharedPreference {
    private static SharedPreferences INSTANCE;

    public static void spInit(Application app) {
        if (INSTANCE == null) {
            INSTANCE = app.getSharedPreferences(Constant.SHARED_KEY, MODE_PRIVATE);
        }
    }


    public static SharedPreferences getINSTANCE() {
        return INSTANCE;
    }
}
