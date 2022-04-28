package com.example.ourdiary.local;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 *通讯录的表，包含名字、电话
 *@author home
 *@time 2021/4/24 22:42
*/
@Entity(tableName = "contact_table")
public class Contact {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "contacts_name")
    private String contact_name;
    @ColumnInfo(name = "contacts_phone_number")
    private String contact_phone_number;

    public Contact(String contact_name, String contact_phone_number) {
        this.contact_name = contact_name;
        this.contact_phone_number = contact_phone_number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getContact_phone_number() {
        return contact_phone_number;
    }

    public void setContact_phone_number(String contact_phone_number) {
        this.contact_phone_number = contact_phone_number;
    }
}
