package com.example.ourdiary.contacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ourdiary.R;
import com.example.ourdiary.db.room.contact_database.ContactViewModel;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends FragmentActivity implements View.OnClickListener,
        LatterSortLayout.OnTouchingLetterChangedListener {

    /**get_id*/
    private int topicId;

    /**UI*/
    private RelativeLayout RL_contacts_content;
    private TextView TV_contacts_title;
    private EditText EDT_main_contacts_search;
    private LatterSortLayout STL_contacts;
    private ImageView IV_contacts_add;
    private TextView TV_contact_short_sort;

    /**room*/

    /**RecyclerView*/
    private RecyclerView RecyclerView_contacts;
    private ContactsAdapter contactsAdapter;
    private LinearLayoutManager layoutManager;

    //Contacts list from DB
    private List<ContactsEntity> contactsNamesList;

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

        IV_contacts_add = findViewById(R.id.IV_contacts_add);
        IV_contacts_add.setOnClickListener(this);

        /**后面加上用intent获取标题功能*/
        TV_contacts_title = findViewById(R.id.TV_contacts_title);
        String diaryTitle = getIntent().getStringExtra("diaryTitle");
        if (diaryTitle == null) {
            diaryTitle = "Contacts";
        }
        TV_contacts_title.setText(diaryTitle);

        RecyclerView_contacts = findViewById(R.id.rv_contacts);
        contactsNamesList = new ArrayList<>();

        //initTopicAdapter();
    }

    private void initTopicAdapter() {
        /**初始化recyclerview*/
        layoutManager = new LinearLayoutManager(this);
        RecyclerView_contacts.setLayoutManager(layoutManager);
        RecyclerView_contacts.setHasFixedSize(true);
        final ContactsAdapter contactsAdapter = new ContactsAdapter(new ContactsAdapter.ContactDiff(), this, topicId);
        RecyclerView_contacts.setAdapter(contactsAdapter);
        mContactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        mContactViewModel.getAllContacts().observe(this, contacts -> {


        } );
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.IV_contacts_add:
                ContactsDetailDialogFragment contactsDetailDialogFragment =
                        ContactsDetailDialogFragment.newInstance(ContactsDetailDialogFragment.ADD_NEW_CONTACTS,
                                "", "", topicId);
                contactsDetailDialogFragment.show(getSupportFragmentManager(), "contactsDetailDialogFragment");
                break;
        }
    }

    @Override
    public void onTouchingLetterChanged(String s) {

    }
}