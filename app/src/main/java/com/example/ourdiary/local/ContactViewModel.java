package com.example.ourdiary.local;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


public class ContactViewModel extends AndroidViewModel {

    private ContactRepository contactRepository;
    private final LiveData<List<Contact>> mAllContacts;


    public ContactViewModel(@NonNull Application application) {
        super(application);
        contactRepository = new ContactRepository(application);
        mAllContacts = contactRepository.getAllContacts();
    }

    public LiveData<List<Contact>> getAllContacts() {
        return  contactRepository.getAllContacts();
    }

    public void insert(Contact contact) { contactRepository.insert(contact); }

    public void deleteOne(Contact contact) { contactRepository.deleteOne(contact); }

    public void deleteAll() { contactRepository.deleteAll(); }

    public void update(Contact contact) { contactRepository.update(contact); }
}
