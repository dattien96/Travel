package com.example.dattienbkhn.travel.database.room;

import android.app.Application;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import com.example.dattienbkhn.travel.database.room.dao.CommonDao;
import com.example.dattienbkhn.travel.database.room.dao.CountryDao;
import com.example.dattienbkhn.travel.database.room.dao.UserDao;
import com.example.dattienbkhn.travel.model.app.City;
import com.example.dattienbkhn.travel.model.app.Comment;
import com.example.dattienbkhn.travel.model.app.Country;
import com.example.dattienbkhn.travel.model.app.Place;
import com.example.dattienbkhn.travel.model.app.PlaceInfor;
import com.example.dattienbkhn.travel.model.app.User;

import static com.example.dattienbkhn.travel.utils.Constant.ROOM_NAME;
import static com.example.dattienbkhn.travel.utils.Constant.ROOM_VERSION;

/**
 * Created by dattienbkhn on 07/02/2018.
 */
@Database(entities = {City.class, Country.class, Place.class, Comment.class,
        PlaceInfor.class}
        , version = ROOM_VERSION)

public abstract class TravelRoom extends RoomDatabase {

    //dao
    public abstract UserDao getUserDao();
    public abstract CountryDao getCountryDao();
    public abstract CommonDao getCommonDao();

    private static TravelRoom INSTANCE;

    public static TravelRoom getRoom(Context ctx) {
        if (INSTANCE == null) {
            throw new RuntimeException("Need createRoom in application first");
        }
        return INSTANCE;
    }

    public static TravelRoom createRoomDb(Application ctx) {
        return Room.databaseBuilder(ctx, TravelRoom.class, ROOM_NAME)
                // .allowMainThreadQueries() //cho phép query ở main Thread
                .build();
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Since we didn't alter the table, there's nothing else to do here.
            //Ở đây giả sử là từ ver1 lên 2 chugns ta k cso thay đổi j nên empty method này
            //nhưng nếu có,ví dụ thêm table hay trường thì cần viết exeSQl thêm đó ở đấy
        }
    };
}
