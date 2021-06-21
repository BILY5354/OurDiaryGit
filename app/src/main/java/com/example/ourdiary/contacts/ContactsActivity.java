package com.example.ourdiary.contacts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.ourdiary.R;
import com.example.ourdiary.db.room.contact_database.Contact;
import com.example.ourdiary.db.room.contact_database.ContactViewModel;
import com.example.ourdiary.shared.gui.LetterComparator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.sourceforge.pinyin4j.PinyinHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContactsActivity extends AppCompatActivity implements
        LatterSortLayout.OnTouchingLetterChangedListener{

    /**get_id*/
    private int topicId;

    /**UI*/
    private RelativeLayout RL_contacts_content;
    private TextView TV_contacts_title;
    private EditText EDT_main_contacts_search;
    private LatterSortLayout STL_contacts;
    private ImageView IV_contacts_add;
    private TextView TV_contact_short_sort;
    FloatingActionButton fab_contact_add;

    /** RecyclerView*/
    private RecyclerView recyclerView_contacts;
    private ContactsAdapter contactsAdapter;
    //private LinearLayoutManager layoutManager;

    /** Room*/
    private ContactViewModel mContactViewModel;

    //Contacts list from DB
    private List<ContactsEntity> contactsNamesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        setTitle("通讯录");

        TV_contact_short_sort = findViewById(R.id.tv_contact_short_sort);
        TV_contact_short_sort.setBackgroundResource(R.color.color_gray);

        STL_contacts = (LatterSortLayout) findViewById(R.id.lsl_contacts);
        STL_contacts.setSortTextView(TV_contact_short_sort);
        STL_contacts.setOnTouchingLetterChangedListener(this);

        fab_contact_add = findViewById(R.id.fab_contact_add);

        contactsNamesList = new ArrayList<>();

        /* We need to get the Title and set then transited by Intent. */
        TV_contacts_title = findViewById(R.id.TV_contacts_title);
        String diaryTitle = getIntent().getStringExtra("contactTitle");
        if (diaryTitle == null) {
            diaryTitle = "Contacts";
        }
        TV_contacts_title.setText(diaryTitle);
        /* TopicId too. */
        topicId = getIntent().getIntExtra("topicId", -1);
        if (topicId == -1) {
            finish();
        }

        initTopicAdapter();//初始化recyclerview和新增的fab按钮
        loadContacts();//载入所有的contacts到list中，并完成排序功能
        receiveResultsWithFra();//宿主（本）Activity与Fragment通信，获取信息

    }

    /**
     * Display the options menu，We also need reload Contacts list when use search function. If it
     *  not be reloaded, it will only show a certain number of contacts, which will be determined by
     *  the results of the fuzzy query. But the contacts name are wrong.
     * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.contact_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setMaxWidth(720);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                String pattern = s.trim();//去掉两边的空格

                mContactViewModel.findContactsWithPattern(1, pattern).observe(ContactsActivity.this, new Observer<List<Contact>>() {
                    @Override
                    public void onChanged(List<Contact> contacts) {

                        contactsAdapter.setShowQueryListSign(true);
                        contactsAdapter.submitList(contacts);
                        contactsAdapter.notifyDataSetChanged();
                    }
                });

                return true;
            }
        });

        return true;
    }

    /**
     * We need to add the Contacts data to list that the data can be sorted.
     * We can use sizeof() function to know how many data in the list.
     * Because we can't sort in the database but we want listed the sorted contacts.
    */
    private void loadContacts() {

        mContactViewModel.getSpecificContacts(topicId).observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {

                contactsNamesList.clear();

                for (int i = 0; i < contacts.size(); i++) {
                    contactsNamesList.add(new ContactsEntity(
                            contacts.get(i).getId(),
                            contacts.get(i).getContacts_name(),
                            contacts.get(i).getContacts_phone_number(),
                            contacts.get(i).getStrId()));//photo先塞入数据库数据 后期是要放图片的
                }

                contactsNamesList = sortContacts(contactsNamesList);

            }
        });

        /*动态获取livedata通讯录总量， 不过并没有必要这么写*/
//        mContactViewModel.getSpecificTopicIdContactCount(1).observe(this, new Observer<Integer>() {
//            @Override
//            public void onChanged(Integer integer) { //integer就是对应topic id通讯录的总数
////              Log.d("test", "now the total contacts number is " + String.valueOf(integer));
//                contactsNamesList.clear();//每次数据改变，都要重新填充一次list列表，让新的数据加载进去
//            }
//        });

    }

    /**对列表进行排序*/
    private List<ContactsEntity> sortContacts(List<ContactsEntity> sortContactsEntity) {

        for (ContactsEntity contactsEntity : sortContactsEntity) {
            String sortString = contactsEntity.getName().substring(0, 1).toUpperCase();
            sortContactsCN(contactsEntity, sortString);
        }
        Collections.sort(sortContactsEntity, new LetterComparator());
        return sortContactsEntity;
    }

    /** Set the SortLetters in List in order to Chinese.*/
    private String sortContactsCN(ContactsEntity contactsEntity, String sortString) {

        if (sortString.matches("[\\u4E00-\\u9FA5]")) {
            String[] arr = PinyinHelper.toHanyuPinyinStringArray(sortString.trim().charAt(0));
            sortString = arr[0].substring(0, 1).toUpperCase();
        }
        if (sortString.matches("[A-Z]")) {
            contactsEntity.setSortLetters(sortString.toUpperCase());
        } else {
            contactsEntity.setSortLetters("#");
        }
        return sortString;
    }


    /** Click on the sidebar to jump to the corresponding word position*/
    @Override
    public void onTouchingLetterChanged(String s) {

        int position = contactsAdapter.getPositionForSection(s.charAt(0));
        if (position != -1) {
            recyclerView_contacts.getLayoutManager().scrollToPosition(position);
        }
    }


    /**
     * Initialize RecyclerView and add the Room observer to get contacts and set the listener for fab
     *@author home
     *@time 2021/5/23 12:26
    */
    private void initTopicAdapter() {

        recyclerView_contacts = findViewById(R.id.rv_contacts);
        contactsAdapter = new ContactsAdapter(new ContactsAdapter.ContactDiff(),
                this, contactsNamesList, topicId);

        recyclerView_contacts.setAdapter(contactsAdapter);
        recyclerView_contacts.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_contacts.setHasFixedSize(true);

        mContactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        mContactViewModel.getSpecificContacts(topicId).observe(this, contacts -> {

            contactsAdapter.submitList(contacts);

        });

        // add new Contact button
        fab_contact_add.setOnClickListener(add_contact -> {
            // contactsId is -1 because it will create new contact not get specific contact
            ContactsDetailDialogFragment contactsDetailDialogFragment =
                    ContactsDetailDialogFragment.newInstance(false,-1,
                            "", "", topicId);
            contactsDetailDialogFragment.show(getSupportFragmentManager(), "contactsDetailDialogFragment");
        });

    }

    /**
     * use getSupportFragmentManager() to make communication between Activity and Fragment.
     * all database operation will be done in this Activity, Fragment just transmit the value (Contact) here
     */
    private void receiveResultsWithFra() {

        //add one
        getSupportFragmentManager().setFragmentResultListener("contacts_detail_fg_add",
                this, new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                        String result_name, result_phone_number;
                        result_name = bundle.getString("contacts_detail_fg_add_name");
                        result_phone_number = bundle.getString("contacts_detail_fg_add_phone_number");
                        Contact contact = new Contact(result_name, result_phone_number, topicId);
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
                        Contact contact = new Contact(result_name, result_phone_number, topicId);
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
                                "delete_contact_phone_number", topicId);
                        contact.setId(result_id);
                        mContactViewModel.deleteOne(contact);
                    }
                });

    }
    

}