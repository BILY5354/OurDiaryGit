package com.example.ourdiary.db.room.topic_database;


import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

/**
 * The TopicEntry and TopicEntry is one to many relationships. Because entry_id is the parent entity
 *  that corresponds to two entities in TopicOrder which is called topic_order_ref_topic_id and
 *  topic_order_order_in_parent.
 * So, we need to define one-to-many relationships.
 *@time 2021/6/14 1:55
*/
public class TopicEntryAndOrder {

    @Embedded public TopicEntry topicEntry;
    @Relation(
            parentColumn =  "entryId",
            entityColumn = "topic_order_ref_topic_id"
    )
    public List<TopicOrder> topicOrders;

}
