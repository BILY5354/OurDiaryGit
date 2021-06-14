package com.example.ourdiary.db.room.topic_database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TopicEntryDao {

    @Insert
    void insertTopicEntry(TopicEntry... topicEntries);

    @Update
    void updateTopicEntry(TopicEntry... topicEntries);

    @Delete
    void deleteTopicEntry(TopicEntry... topicEntries);

    @Transaction
    @Query("SELECT * FROM TopicEntry")
    LiveData<List<TopicEntryAndOrder>> getTopicEntryAndOrder();
}
