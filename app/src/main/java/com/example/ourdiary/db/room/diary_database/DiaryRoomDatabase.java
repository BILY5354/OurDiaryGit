package com.example.ourdiary.db.room.diary_database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * 单例模式！单例模式！
 * Version had added the new column "diaries_contacts_ref_topic_id"
 *@time 2021/4/16 20:10
*/
@Database(entities = {Diary.class}, version = 2, exportSchema = false)
public abstract class DiaryRoomDatabase extends RoomDatabase {

    public abstract DiaryDao getDiaryDao();

    private static DiaryRoomDatabase INSTANCE;

    static synchronized DiaryRoomDatabase getDiaryDatabase(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DiaryRoomDatabase.class,
                    "diary_database")
                    .addMigrations(MIGRATION_1_2)
                    .build();
        }
        return INSTANCE;
    }


    static final Migration MIGRATION_1_2 = new Migration(1,2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE diary_table "
                    + " ADD COLUMN diaries_contacts_ref_topic_id INTEGER NOT NULL DEFAULT 0");
        }
    };
}
