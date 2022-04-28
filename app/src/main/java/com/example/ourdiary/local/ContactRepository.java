package com.example.ourdiary.local;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ContactRepository {

    private ContactDao contactDao;
    private LiveData<List<Contact>> liveContactsData;

    ContactRepository(Application application) {
        AppDataBase db = AppDataBase.getMyBlogDatabase(application);
        contactDao = db.contactDao();
        liveContactsData = contactDao.getAllContactsLive();
    }

    /**获取全部通讯录*/
    public LiveData<List<Contact>> getAllContacts() {
        return liveContactsData;
    }

    void insert (Contact contact) {
        AppDataBase.databaseWriteExecutor.execute( () -> {
            contactDao.insertContact(contact);
        });
    }

    void update (Contact contact) {
        AppDataBase.databaseWriteExecutor.execute( () -> {
            contactDao.updateContact(contact);
        });
    }

    void deleteOne (Contact contact) {
        AppDataBase.databaseWriteExecutor.execute( () -> {
            contactDao.deleteContact(contact);
        });
    }

    void deleteAll () {
        AppDataBase.databaseWriteExecutor.execute( () -> {
            contactDao.deleteAllContacts();
        });
    }

}
