package com.example.ourdiary.contacts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.ourdiary.R;
import com.example.ourdiary.db.room.contact_database.Contact;

public class ContactListAdapter extends ListAdapter<Contact, ContactViewHolder> {


    protected ContactListAdapter(@NonNull DiffUtil.ItemCallback<Contact> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_contacts_item, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {

    }
}
