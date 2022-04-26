package com.example.ourdiary.shared.gui;

import com.example.ourdiary.contacts_page.ContactsEntity;

import java.util.Comparator;

public class LetterComparator implements Comparator<ContactsEntity> {

    @Override
    public int compare(ContactsEntity lhs, ContactsEntity rhs) {
        return lhs.getSortLetters().compareTo(rhs.getSortLetters());
    }
}
