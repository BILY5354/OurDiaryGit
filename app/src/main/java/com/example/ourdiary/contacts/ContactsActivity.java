package com.example.ourdiary.contacts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ourdiary.R;
import com.example.ourdiary.db.room.contact_database.Contact;
import com.example.ourdiary.db.room.contact_database.ContactViewModel;
import com.example.ourdiary.db.room.diary_database.Diary;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity implements
        LatterSortLayout.OnTouchingLetterChangedListener {

    /**get_id*/
    private int topicId=1;

    /**UI*/
    private RelativeLayout RL_contacts_content;
    private TextView TV_contacts_title;
    private EditText EDT_main_contacts_search;
    private LatterSortLayout STL_contacts;
    private ImageView IV_contacts_add;
    private TextView TV_contact_short_sort;
    FloatingActionButton contact_add;

    /**RecyclerView*/
    private RecyclerView recyclerView_contacts;
    private ContactsAdapter contactsAdapter;
    private LinearLayoutManager layoutManager;

    /**room*/
    private ContactViewModel mContactViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        TV_contact_short_sort = findViewById(R.id.tv_contact_short_sort);
        TV_contact_short_sort.setBackgroundResource(R.color.color_gray);

        STL_contacts = findViewById(R.id.lsl_contacts);
        STL_contacts.setSortTextView(TV_contact_short_sort);
        STL_contacts.setOnTouchingLetterChangedListener(this);

        contact_add = findViewById(R.id.fab_contact_add);

        /**后面加上用intent获取标题功能*/
        TV_contacts_title = findViewById(R.id.TV_contacts_title);
        String diaryTitle = getIntent().getStringExtra("diaryTitle");
        if (diaryTitle == null) {
            diaryTitle = "Contacts";
        }
        TV_contacts_title.setText(diaryTitle);

        initTopicAdapter();//初始化recyclerview

        contact_add.setOnClickListener(add_contact -> {
            //contactsId is -1 because it will create new contact not get specific contact
            ContactsDetailDialogFragment contactsDetailDialogFragment =
                    ContactsDetailDialogFragment.newInstance(false,-1,"", "", topicId);
             contactsDetailDialogFragment.show(getSupportFragmentManager(), "contactsDetailDialogFragment");
        });

        /**get contacts from ContactsDetailDialogFragment*/
        //add one
        getSupportFragmentManager().setFragmentResultListener("contacts_detail_fg_add",
                this, new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                        String result_name, result_phone_number;
                        result_name = bundle.getString("contacts_detail_fg_add_name");
                        result_phone_number = bundle.getString("contacts_detail_fg_add_phone_number");
                        Contact contact = new Contact(result_name, result_phone_number, 1);
                        mContactViewModel.insert(contact);
                    }
                });
        //update one
        getSupportFragmentManager().setFragmentResultListener("contacts_detail_fg_update",
                this, new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                        int result_id = bundle.getInt("contacts_detail_fg_update_id");
                        String result_name, result_phone_number;
                        result_name = bundle.getString("contacts_detail_fg_update_name");
                        result_phone_number = bundle.getString("contacts_detail_fg_update_phone_number");
                        Contact contact = new Contact(result_name, result_phone_number, 1);
                        contact.setId(result_id);
                        mContactViewModel.update(contact);
                    }
                });
        //delete one
        getSupportFragmentManager().setFragmentResultListener("contacts_detail_fg_delete_one",
                this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                int result_id = bundle.getInt("contacts_detail_fg_delete_one_id");
                Contact contact = new Contact("delete_contact_name",
                        "delete_contact_phone_number", 1);
                contact.setId(result_id);
                mContactViewModel.deleteOne(contact);
            }
        });
    }



    @Override
    public void onTouchingLetterChanged(String s) {

    }

    /**
     *Initialize RecyclerView and add the Room observer to get contacts
     *@author home
     *@time 2021/5/23 12:26
    */
    private void initTopicAdapter() {
        recyclerView_contacts = findViewById(R.id.rv_contacts);
        final ContactsAdapter contactsAdapter = new ContactsAdapter(
                new ContactsAdapter.ContactDiff(), this, topicId);
        recyclerView_contacts.setAdapter(contactsAdapter);
        recyclerView_contacts.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_contacts.setHasFixedSize(true);
        mContactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        mContactViewModel.getAllContacts().observe(this, contacts -> {
            contactsAdapter.submitList(contacts);
        });

    }


}