package com.example.ourdiary.db.room.topic_database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TopicOrderDao {

    @Insert
    void insertTopicOrder(TopicOrder... topicOrders);

    @Update
    void updateTopicOrder(TopicOrder... topicOrders);

    @Delete
    void deleteTopicOrder(TopicOrder... topicOrders);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * " +
            "FROM TopicEntry " +
            "LEFT OUTER JOIN TopicOrder " +
            "ON TopicEntry.entryId = TopicOrder.topic_order_ref_topic_id " +
            "ORDER BY TopicOrder.topic_order_order_in_parent DESC")
    LiveData<List<TopicEntryAndOrder>> getAllTopicEntryAndOrder();//this is the function named getSelectTopic in myDiary


}
