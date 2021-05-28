package com.example.ourdiary.db.room.contact_database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {

    private ContactRepository contactRepository;

    public ContactViewModel(@NonNull Application application) {
        super(application);
        contactRepository = new ContactRepository(application);
    }

    public LiveData<List<Contact>> getAllContacts() {
        return  contactRepository.getAllContacts(); }

    public LiveData<List<Contact>> getSpecificContacts(int ref_topic_id) {
        return  contactRepository.getSpecificTopicIdContact(ref_topic_id); }

    /**获取对应topic id通讯录的数量*/
    public LiveData<Integer> getSpecificTopicIdContactCount(int ref_topic_id) {
        return contactRepository.getSpecificTopicIdContactCount(ref_topic_id); }

    public void insert(Contact contact) { contactRepository.insert(contact); }

    public void deleteOne(Contact contact) { contactRepository.deleteOne(contact); }

    public void deleteAll(Contact contact) { contactRepository.deleteAll(); }

    public void update(Contact contact) { contactRepository.update(contact); }
}
