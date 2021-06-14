package com.example.ourdiary.db.room.topic_database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TopicEntry {

    @PrimaryKey(autoGenerate = true)
    private int entryId;

    @ColumnInfo(name = "topic_name")
    private String topic_name;
    @ColumnInfo(name = "topic_type")
    private int topic_type;
    @ColumnInfo(name = "topic_order")
    private String topic_order;
    @ColumnInfo(name = "topic_subtitle")
    private String topic_subtitle;
    @ColumnInfo(name = "topic_color")
    private Long topic_color;

    public TopicEntry(String topic_name, int topic_type, String topic_order,
                      String topic_subtitle, Long topic_color) {
        this.topic_name = topic_name;
        this.topic_type = topic_type;
        this.topic_order = topic_order;
        this.topic_subtitle = topic_subtitle;
        this.topic_color = topic_color;
    }

    public int getEntryId() {
        return entryId;
    }

    public void setEntryId(int entryId) {
        this.entryId = entryId;
    }

    public String getTopic_name() {
        return topic_name;
    }

    public void setTopic_name(String topic_name) {
        this.topic_name = topic_name;
    }

    public int getTopic_type() {
        return topic_type;
    }

    public void setTopic_type(int topic_type) {
        this.topic_type = topic_type;
    }

    public String getTopic_order() {
        return topic_order;
    }

    public void setTopic_order(String topic_order) {
        this.topic_order = topic_order;
    }

    public String getTopic_subtitle() {
        return topic_subtitle;
    }

    public void setTopic_subtitle(String topic_subtitle) {
        this.topic_subtitle = topic_subtitle;
    }

    public Long getTopic_color() {
        return topic_color;
    }

    public void setTopic_color(Long topic_color) {
        this.topic_color = topic_color;
    }
}
