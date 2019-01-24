package com.team12.warofwords.DB;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.team12.warofwords.DAO.IntermediateDAO;
import com.team12.warofwords.DAO.NormalDAO;
import com.team12.warofwords.entity.Intermediate;
import com.team12.warofwords.entity.Normal;

@Database(entities = {Normal.class, Intermediate.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract NormalDAO normalDAO();

    public abstract IntermediateDAO intermediateDAO();


    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = buildDatabase(context);
        }
        return INSTANCE;
    }

    private static AppDatabase buildDatabase(final Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "my-database")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
//                .addMigrations(MIGRATION_1_2)
    }



    public static void destroyInstance() {
        INSTANCE = null;
    }
}