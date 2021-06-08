package com.example.ourdiary.db.room.topic_database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.ourdiary.db.room.contact_database.ContactDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {TopicEntry.class, TopicOrder.class}, version = 1, exportSchema = false)
public abstract class TopicRoomDatabase extends RoomDatabase {

    public abstract TopicEntryDao topicEntryDao();
    public abstract TopicOrderDao topicOrderDao();

    private static TopicRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static synchronized TopicRoomDatabase getTopicRoomDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TopicRoomDatabase.class,
                    "Topic_database").build();
        }
        return INSTANCE;
    }


}
