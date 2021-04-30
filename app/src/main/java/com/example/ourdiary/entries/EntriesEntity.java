package com.example.ourdiary.entries;

import java.util.Date;

/**
 *用于帮助找日记id的类，参考自mydiary
 *@author home
 *@time 2021/4/29 16:28
*/
public class EntriesEntity {

    private int id;
    private String title;
    private String content;

    public EntriesEntity(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
