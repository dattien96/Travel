package com.example.dattienbkhn.travel.utils;

/**
 * Created by dattienbkhn on 31/01/2018.
 */

public class Constant {
    //Room
    public static final int ROOM_VERSION = 2;
    public static final String ROOM_NAME = "Travel-database";

    //Retrofit
    //home hn
    public static String TRAVEL_API_BASE_URL = "http://192.168.1.4:5000/api/travel/";
    public static final String VOD_BASE_URL = "rtmp://192.168.1.4:1935/vod/";
    public static final String RTMP_BASE_URL = "rtmp://192.168.1.4:1935/live/";
    public static final String SOCKET_IO_URL = "http://192.168.1.4:3000";

    //bk B1
    //public static String TRAVEL_API_BASE_URL = "http://10.11.204.17:5000/api/travel/";
    //public static final String VOD_BASE_URL = "rtmp://10.11.204.17:1935/vod/";
    //public static final String RTMP_BASE_URL = "rtmp://10.11.204.17:1935/live/";
    //public static final String SOCKET_IO_URL = "http://10.11.204.17:3000";


    public static String MAP_API_BASE_URL = "https://maps.googleapis.com/maps/";
    public static String IP_API_BASE_URL = "https://api.ipify.org?format=json";
    public static String LOCATION_API_BASE_URL = "https://ipapi.co/";
    public static String WEATHER_API_BASE_URL = "http://api.openweathermap.org/data/2.5/";
    public static String FACEBOOK_API_BASE_URL = "https://graph.facebook.com/";
    public static String GOOGLE_INFOR_API_BASE_URL = "http://picasaweb.google.com/data/entry/api/";



    public static final int STREAM_TYPE_LIVE_RTMP = 0;
    public static final int STREAM_TYPE_VOD_RTMP = 1;

    //shared pref
    public static String SHARED_KEY = "travel_pref";
    public static String SHARED_PLACE_SEE_TYPE = "see";
    public static String SHARED_PLACE_FOODIES_TYPE = "foodies";
    public static String SHARED_PLACE_DRINK_TYPE = "drink";
    public static String SHARED_PLACE_STAY_TYPE = "stay";
    public static String SHARED_LOGIN_STATE = "login";
    public static String SHARED_LOGIN_ACC_ID = "acc_id";
    public static String SHARED_FACEBOOK_TOKEN = "fb_token";
    public static String SHARED_FACEBOOK_ID = "fb_id";
    public static String SHARED_GOOGLE_ID = "gg_id";
    public static String SHARED_GOOGLE_TOKEN = "gg_token";
    public static String SHARED_LOGIN_TYPE = "login_type";
    public static String SHARED_LOGIN_FACEBOOK = "login_facebook";
    public static String SHARED_LOGIN_GOOGLE = "login_google";
    public static String SHARED_LOGIN_APP = "login_app";

    public static String SHARED_LAST_LOCATION = "last_location_of_user";

    //gac
    public static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    public static final int MY_PERMISSIONS_REQUEST_ACCESS_CAMERA = 2;
    public static final int MY_PERMISSIONS_REQUEST_ACCESS_RECORD = 3;


    //stream socket
    public static String CLIENT_STREAM_EMIT = "client_stream_emit";
    public static String SERVER_STREAM_EMIT = "server_stream_emit";


}
