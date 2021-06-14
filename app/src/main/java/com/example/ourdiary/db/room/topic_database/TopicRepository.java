package com.example.ourdiary.db.room.topic_database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TopicRepository {

    private TopicOrderDao mTopicOrderDao;
    private TopicEntryDao mTopicEntryDao;
    private LiveData<List<TopicEntry>> mAllTopicEntriesLive;

    TopicRepository(Application application) {
        TopicRoomDatabase db = TopicRoomDatabase.getTopicRoomDatabase(application);
        mTopicEntryDao = db.topicEntryDao();
        mTopicOrderDao = db.topicOrderDao();
        mAllTopicEntriesLive = mTopicEntryDao.getAllTopicEntriesLive();
    }


    /** start TopicEntry */
    void insertTopicEntry (TopicEntry topicEntry) {
        TopicRoomDatabase.databaseWriteExecutor.execute( () -> {
            mTopicEntryDao.insertTopicEntry(topicEntry);
        });
    }

    void updateTopicEntry (TopicEntry topicEntry) {
        TopicRoomDatabase.databaseWriteExecutor.execute( () -> {
            mTopicEntryDao.updateTopicEntry(topicEntry);
        });
    }

    void deleteOneTopicEntry (TopicEntry topicEntry) {
        TopicRoomDatabase.databaseWriteExecutor.execute( () -> {
            mTopicEntryDao.deleteTopicEntry(topicEntry);
        });
    }

    public LiveData<List<TopicEntry>> getAllTopicEntriesLive() {
        return mAllTopicEntriesLive;
    }

    public LiveData<List<TopicEntryAndOrder>> getTopicEntryAndOrder() {
        //this is the function named getSelectTopic in myDiary
        return mTopicEntryDao.getTopicEntryAndOrder();
    }
    /** end TopicOrder */


    /** start TopicOrder */
    void insertTopicOrder (TopicOrder topicOrder) {
        TopicRoomDatabase.databaseWriteExecutor.execute( () -> {
            mTopicOrderDao.insertTopicOrder(topicOrder);
        });
    }

    void updateTopicOrder (TopicOrder topicOrder) {
        TopicRoomDatabase.databaseWriteExecutor.execute( () -> {
            mTopicOrderDao.updateTopicOrder(topicOrder);
        });
    }

    void deleteOneTopicOrder (TopicOrder topicOrder) {
        TopicRoomDatabase.databaseWriteExecutor.execute( () -> {
            mTopicOrderDao.deleteTopicOrder(topicOrder);
        });
    }
    /** end TopicOrder */
}
