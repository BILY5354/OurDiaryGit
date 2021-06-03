package com.example.ourdiary.db.room.contact_database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ContactRepository {

    private ContactDao mContactDao;
    private LiveData<List<Contact>> mAllContacts;

    ContactRepository(Application application) {
        ContactRoomDatabase db = ContactRoomDatabase.getContactDatabase(application);
        mContactDao = db.contactDao();
        mAllContacts = mContactDao.getAllContactsLive();
    }

    public LiveData<List<Contact>> getAllContacts() {
        return mAllContacts;
    }

    /**获取对应topic id的通讯录*/
    public LiveData<List<Contact>> getSpecificTopicIdContact(int ref_topic_id) {
        return mContactDao.getSpecificTopicIdContact(ref_topic_id);
    }

    /**获取对应topic id通讯录的数量*/
    public LiveData<Integer> getSpecificTopicIdContactCount(int ref_topic_id) {
        return  mContactDao.getSpecificTopicIdContactCount(ref_topic_id);
    }

    /**模糊匹配查询 前后加入 % 是模糊匹配，不然按照整一个字段进行查找*/
    public LiveData<List<Contact>> findContactsWithPattern(int ref_topic_id, String pattern) {
        return mContactDao.findContactsWithPattern(ref_topic_id,"%" + pattern + "%");
    }

    void insert (Contact contact) {
        ContactRoomDatabase.databaseWriteExecutor.execute( () -> {
            mContactDao.insertContacts(contact);
        });
    }

    void update (Contact contact) {
        ContactRoomDatabase.databaseWriteExecutor.execute( () -> {
            mContactDao.updateContacts(contact);
        });
    }

    void deleteOne (Contact contact) {
        ContactRoomDatabase.databaseWriteExecutor.execute( () -> {
            mContactDao.deleteContacts(contact);
        });
    }

    void deleteAll () {
        ContactRoomDatabase.databaseWriteExecutor.execute( () -> {
            mContactDao.deleteAllContacts();
        });
    }
}
