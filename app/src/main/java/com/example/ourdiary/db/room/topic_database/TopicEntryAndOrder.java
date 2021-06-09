package com.example.ourdiary.db.room.topic_database;


import androidx.room.ColumnInfo;

public class TopicEntryAndOrder {

    public int entryId;

    @ColumnInfo(name="topic_name")
    public String topic_name;

    @ColumnInfo(name="topic_type")
    public int topic_type;

    @ColumnInfo(name="topic_order")
    public int topic_order;

    @ColumnInfo(name="topic_subtitle")
    public String topic_subtitle;

    @ColumnInfo(name="topic_color")
    public Long topic_color;

    @ColumnInfo(name="topic_order_ref_topic_id")
    public int topic_order_ref_topic_id;

    @ColumnInfo(name="topic_order_order_in_parent")
    public int topic_order_order_in_parent;


}
