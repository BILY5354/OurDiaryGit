package com.example.ourdiary.db.room.topic_database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.ourdiary.db.room.contact_database.ContactDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {TopicEntry.class, TopicOrder.class}, version = 1, exportSchema = false)
public abstract class TopicRoomDatabase extends RoomDatabase {

    public abstract TopicEntryDao topicEntryDao();
    public abstract TopicOrderDao topicOrderDao();

    private static TopicRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 10;//This is the number of databaseWriteExecutor in TopicRepository, don't set the value less than TopicRepository
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static synchronized TopicRoomDatabase getTopicRoomDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TopicRoomDatabase.class,
                    "Topic_database")
                    .addCallback(sRoomDataBaseCallBack)
                    .build();
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDataBaseCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute( () -> {
                TopicEntryDao entryDao = INSTANCE.topicEntryDao();
                TopicOrderDao orderDao = INSTANCE.topicOrderDao();

                TopicEntry topicEntry = new TopicEntry("first", 1, null, null, null);
                TopicOrder topicOrder = new TopicOrder(1,0);
                entryDao.insertTopicEntry(topicEntry);
                orderDao.insertTopicOrder(topicOrder);
            });
        }
    };

}
