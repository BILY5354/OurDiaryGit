package com.example.ourdiary.contact;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.ourdiary.R;
import com.example.ourdiary.local.ContactViewModel;


import java.util.List;

public class ContactActivity extends AppCompatActivity {

    /**
     * Room
     */
    private ContactViewModel mContactViewModel;

    private RecyclerView rv_contact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        /**使标actionBar隐藏*/
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        rv_contact = findViewById(R.id.RV_contact);
        final ContactAdapter contactAdapter = new ContactAdapter(new ContactAdapter.ContactDiff());
        rv_contact.setAdapter(contactAdapter);
        rv_contact.setLayoutManager(new LinearLayoutManager(this));
        mContactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        mContactViewModel.getAllContacts().observe(this, contacts -> {
            contactAdapter.submitList(contacts);
        });
    }

}