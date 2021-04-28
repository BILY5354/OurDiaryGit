package com.example.ourdiary.db.room.diary_database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 *单例模式！单例模式！
 *@author home
 *@time 2021/4/16 20:10
*/
@Database(entities = {Diary.class}, version = 1, exportSchema = false)
public abstract class DiaryRoomDatabase extends RoomDatabase {
    private static DiaryRoomDatabase INSTANCE;

    static synchronized DiaryRoomDatabase getDiaryDatabase(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DiaryRoomDatabase.class,
                    "diary_database").build();
        }
        return INSTANCE;
    }
    public abstract DiaryDao getDiaryDao();
}
