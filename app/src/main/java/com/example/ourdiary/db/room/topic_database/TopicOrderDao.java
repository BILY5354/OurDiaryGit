package com.example.ourdiary.db.room.topic_database;

import androidx.lifecycle.LiveData;
import androidx.room.Query;

import java.util.List;

public interface TopicOrderDao {


    @Query("SELECT * FROM TopicEntry " +
            "LEFT OUTER JOIN TopicOrder " +
            "ON TopicEntry.entryId = TopicOrder.topic_order_ref_topic_id " +
            "ORDER BY TopicOrder.topic_order_order_in_parent DESC")
    LiveData<List<TopicEntryAndOrder>> getSelectTopic();


}
