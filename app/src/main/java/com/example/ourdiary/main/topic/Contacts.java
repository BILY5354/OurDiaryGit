package com.example.ourdiary.main.topic;

import com.example.ourdiary.R;

public class Contacts implements ITopic {

    private String title;
    private int id;
    private int count;
    private int color;
    private boolean pinned = false;

    public Contacts(int id, String title, int color) {
        this.id = id;
        this.title = title;
        this.color = color;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int getType() {
        return TYPE_CONTACTS;
    }

    @Override
    public int getIcon() {
       return R.drawable.iv_new_contacts;

    }

    @Override
    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int getCount() {
        return count;
    }


    @Override
    public int getColor() {
        return color;
    }

    @Override
    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    @Override
    public boolean isPinned() {
        return pinned;
    }
}
