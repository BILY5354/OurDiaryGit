package com.example.ourdiary.contacts_page;

/**
 *将long的contacts_ Id改成了int的
 *@author home
 *@time 2021/5/28 19:12
*/
public class ContactsEntity {

    private int contactsId;
    private String name;
    private String phoneNumber;
    private String photo;
    private String sortLetters;

    public ContactsEntity(int contactsId, String name, String phoneNumber, String photo) {
        this.contactsId = contactsId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.photo = photo;
    }

    public int getContactsId() {
        return contactsId;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPhoto() {
        return photo;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }
}
