package com.example.ourdiary.db.room.contact_database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {

    private ContactRepository mRepository;

    private final LiveData<List<Contact>> mAllContacts;
    private final LiveData<List<Contact>> mSpecificTopicIdContact;

    public ContactViewModel(@NonNull Application application,int ref_topic_id) {
        super(application);
        mRepository = new ContactRepository(application, ref_topic_id);
        mAllContacts = mRepository.getAllContacts();
        mSpecificTopicIdContact = mRepository.getSpecificTopicIdContact(ref_topic_id);
    }

    public LiveData<List<Contact>> getAllContacts() { return  mAllContacts; }

    public LiveData<List<Contact>> getSpecificContacts() { return  mSpecificTopicIdContact; }

    public void insert(Contact contact) { mRepository.insert(contact); }

    public void deleteOne(Contact contact) { mRepository.deleteOne(contact); }

    public void deleteAll(Contact contact) { mRepository.deleteAll(); }

    public void Update(Contact contact) { mRepository.update(contact); }
}
