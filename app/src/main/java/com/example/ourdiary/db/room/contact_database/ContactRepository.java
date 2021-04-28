package com.example.ourdiary.db.room.contact_database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ContactRepository {

    private ContactDao mContactDao;
    private LiveData<List<Contact>> mAllContacts;
    private LiveData<List<Contact>> mSpecificTopicIdContact;

    ContactRepository(Application application, int ref_topic_id) {
        ContactRoomDatabase db = ContactRoomDatabase.getContactDatabase(application);
        mContactDao = db.contactDao();
        mAllContacts = mContactDao.getAllContactsLive();
        mSpecificTopicIdContact = mContactDao.getSpecificTopicIdContact(ref_topic_id);
    }

    LiveData<List<Contact>> getAllContacts() {
        return mAllContacts;
    }

    LiveData<List<Contact>> getSpecificTopicIdContact(int ref_topic_id) {
        return mSpecificTopicIdContact;
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
