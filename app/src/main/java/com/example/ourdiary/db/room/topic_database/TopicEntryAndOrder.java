package com.example.ourdiary.db.room.topic_database;


public class TopicEntryAndOrder {

    public int topic_entry_and_order_id;
    public String topic_name;
    public int topic_type;
    public int topic_order;
    public String topic_subtitle;
    public Long topic_color;
    public int topic_order_ref_topic_id;
    public int topic_order_order_in_parent;

    public int getTopic_entry_and_order_id() {
        return topic_entry_and_order_id;
    }

    public void setTopic_entry_and_order_id(int topic_entry_and_order_id) {
        this.topic_entry_and_order_id = topic_entry_and_order_id;
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

    public int getTopic_order() {
        return topic_order;
    }

    public void setTopic_order(int topic_order) {
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
