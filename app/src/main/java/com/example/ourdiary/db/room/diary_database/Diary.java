package com.example.ourdiary.db.room.diary_database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 *写日记的表，包含日记的标题和日记的内容
 *@author home
 *@time 2021/4/16 19:38
*/
@Entity(tableName = "diary_table")
public class Diary {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "diary_title")
    private String diary_Title;
    @ColumnInfo(name = "diary_content")
    private String diary_Content;
    @ColumnInfo(name = "diaries_contacts_ref_topic_id")
    private int diaries_contacts_ref_topic_id;

    public Diary(String diary_Title, String diary_Content, int diaries_contacts_ref_topic_id) {
        this.diary_Title = diary_Title;
        this.diary_Content = diary_Content;
        this.diaries_contacts_ref_topic_id = diaries_contacts_ref_topic_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getStrId() {
        return String.valueOf(id);
    }

    public String getDiary_Title() {
        return diary_Title;
    }

    public void setDiary_Title(String diary_Title) {
        this.diary_Title = diary_Title;
    }

    public String getDiary_Content() {
        return diary_Content;
    }

    public void setDiary_Content(String diary_Content) {
        this.diary_Content = diary_Content;
    }

    public int getDiaries_contacts_ref_topic_id() {
        return diaries_contacts_ref_topic_id;
    }

    public void setDiaries_contacts_ref_topic_id(int diaries_contacts_ref_topic_id) {
        this.diaries_contacts_ref_topic_id = diaries_contacts_ref_topic_id;
    }
}
