package com.example.ourdiary.db.room.contact_database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 *通讯录的表，包含名字、电话、被参考的topic_id
 *@author home
 *@time 2021/4/24 22:42
*/
@Entity(tableName = "contacts_table")
public class Contact {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "contacts_name")
    private String contacts_name;
    @ColumnInfo(name = "contacts_phone_number")
    private String contacts_phone_number;
    @ColumnInfo(name = "contacts_contacts_ref_topic_id")
    private int contacts_contacts_ref_topic_id;

    public Contact(String contacts_name, String contacts_phone_number, int contacts_contacts_ref_topic_id) {
        this.contacts_name = contacts_name;
        this.contacts_phone_number = contacts_phone_number;
        this.contacts_contacts_ref_topic_id = contacts_contacts_ref_topic_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContacts_name(String contacts_name) {
        this.contacts_name = contacts_name;
    }

    public void setContacts_phone_number(String contacts_phone_number) {
        this.contacts_phone_number = contacts_phone_number;
    }

    public void setContacts_contacts_ref_topic_id(int contacts_contacts_ref_topic_id) {
        this.contacts_contacts_ref_topic_id = contacts_contacts_ref_topic_id;
    }

    public int getId() {
        return id;
    }

    public String getStrId() {
        return String.valueOf(id);
    }

    public String getContacts_name() {
        return contacts_name;
    }

    public String getContacts_phone_number() {
        return contacts_phone_number;
    }

    public int getContacts_contacts_ref_topic_id() {
        return contacts_contacts_ref_topic_id;
    }
}
