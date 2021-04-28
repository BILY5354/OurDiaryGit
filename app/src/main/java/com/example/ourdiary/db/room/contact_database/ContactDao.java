package com.example.ourdiary.db.room.contact_database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDao {

    @Insert
    void insertContacts(Contact... contacts);

    @Update
    void updateContacts(Contact... contacts);

    @Delete
    void deleteContacts(Contact... contacts);

    @Query("DELETE FROM contacts_table")
    void deleteAllContacts();

    @Query("SELECT * FROM contacts_table ORDER BY ID ASC")
    LiveData<List<Contact>>getAllContactsLive();

    /**根据不同的topic查询相对应的通讯录，因为所有的contacts都保存在同一个表中，根据topic_id来区分显示，所有查询的也是一个list*/
    @Query("SELECT * FROM contacts_table WHERE contacts_contacts_ref_topic_id =:ref_topic_id")
    LiveData<List<Contact>> getSpecificTopicIdContact(int ref_topic_id);
}
