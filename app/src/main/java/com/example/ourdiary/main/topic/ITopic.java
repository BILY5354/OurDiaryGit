package com.example.ourdiary.main.topic;

import androidx.annotation.DrawableRes;


/**
 *Have changed the type of id and count long to int.
 *@author home
 *@time 2021/6/14 13:53
 */
public interface ITopic {

    /** The contacts , Mitsuha  and Taki change their cell phone number in this function. */
    int TYPE_CONTACTS = 0;

    /** Mitsuha and Taki write daily diary when their soul change. */
    int TYPE_DIARY = 1;

    /** Mitsuha and Taki add some memo to notice that something can't do. */
    int TYPE_MEMO = 2;

    String getTitle();

    /** For update topic */
    void setTitle(String title);

    int getType();

    int getId();

    @DrawableRes
    int getIcon();

    /** For update count in Main Page */
    void setCount(int count);

    int getCount();

    int getColor();

    /** For update topic */
    void setColor(int color);

    /** For the left swipe */

    void setPinned(boolean pinned);

    boolean isPinned();
}
