package com.example.dattienbkhn.travel;

import android.app.Application;
import android.content.Context;

import com.example.dattienbkhn.travel.database.sharedPreference.TravelSharedPreference;
import com.example.dattienbkhn.travel.network.service.FacebookService;
import com.example.dattienbkhn.travel.network.service.GoogleInforService;
import com.example.dattienbkhn.travel.network.service.IpService;
import com.example.dattienbkhn.travel.network.service.LocationService;
import com.example.dattienbkhn.travel.network.service.MapService;
import com.example.dattienbkhn.travel.network.service.TravelService;
import com.example.dattienbkhn.travel.network.service.WeatherService;
import com.example.dattienbkhn.travel.repository.AppRepository;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * Created by dattienbkhn on 06/02/2018.
 */

public class TravelApplication extends Application {
    private static Context mContext;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        //realm
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("myrealm.realm")
                .schemaVersion(1) // Must be bumped when the schema changes
                .migration(new MyMigration()) // Migration to run instead of throwing an exception
                .build();
        Realm.setDefaultConfiguration(config);

        //facebook init
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        //init room
        //TravelRoom.createRoomDb(this);

        //retrofit init
        TravelService.initTravelService(this);
        MapService.initMapService(this);
        WeatherService.initWeatherService(this);
        IpService.initIpService(this);
        LocationService.initLocationService(this);
        FacebookService.initFacebookService(this);
        GoogleInforService.initGoogleInforService(this);

        //init app repository
        AppRepository.createAllRepo(this);

        //init shared preference
        TravelSharedPreference.spInit(this);

       /* //doan code duoi in ra log ma hash key facebook sdk
        try{
            PackageInfo info = getPackageManager().getPackageInfo("com.example.dattienbkhn.travel", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));

            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }*/
    }

    public static Context getContext() {
        return mContext;
    }

    public class MyMigration implements RealmMigration {
        @Override
        public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

            // DynamicRealm exposes an editable schema
            RealmSchema schema = realm.getSchema();

            // Migrate to version 1: Add a new class.
            // Example:
            // public Person extends RealmObject {
            //     private String name;
            //     private int age;
            //     // getters and setters left out for brevity
            // }
            if (oldVersion == 0) {
                schema.create("PlaceSave")
                        .addField("id", int.class)
                        .addField("name", String.class)
                        .addField("type", String.class)
                        .addField("image", String.class)
                        .addField("view", int.class)
                        .addField("rate", float.class);
                oldVersion++;
            }



        }
    }
}
