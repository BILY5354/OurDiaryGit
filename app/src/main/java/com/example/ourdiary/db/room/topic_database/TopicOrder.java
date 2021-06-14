package com.example.ourdiary.db.room.topic_database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

/**
 *topic_order_ref_topic_id reference TopicEntry table primary key(entryId).
 * orderId is the useless column, because it must use the primary key when use Room.
 *@author home
 *@time 2021/6/8 17:27
*/
//@Entity(foreignKeys = @ForeignKey(
//        entity = TopicEntry.class,
//        parentColumns = "entryId",
//        childColumns = "topic_order_ref_topic_id",
//        onDelete = CASCADE))
@Entity
public class TopicOrder {

    @PrimaryKey(autoGenerate = true)
    private int orderId;
    @ColumnInfo(name = "topic_order_ref_topic_id", index = true)
    private int topic_order_ref_topic_id;
    @ColumnInfo(name = "topic_order_order_in_parent")
    private int topic_order_order_in_parent;

    public TopicOrder(int topic_order_ref_topic_id, int topic_order_order_in_parent) {
        this.topic_order_ref_topic_id = topic_order_ref_topic_id;
        this.topic_order_order_in_parent = topic_order_order_in_parent;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getTopic_order_ref_topic_id() {
        return topic_order_ref_topic_id;
    }

    public void setTopic_order_ref_topic_id(int topic_order_ref_topic_id) {
        this.topic_order_ref_topic_id = topic_order_ref_topic_id;
    }

    public int getTopic_order_order_in_parent() {
        return topic_order_order_in_parent;
    }

    public void setTopic_order_order_in_parent(int topic_order_order_in_parent) {
        this.topic_order_order_in_parent = topic_order_order_in_parent;
    }
}
