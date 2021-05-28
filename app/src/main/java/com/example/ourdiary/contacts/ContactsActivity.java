package com.example.ourdiary.contacts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
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
import com.example.ourdiary.shared.gui.LetterComparator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.sourceforge.pinyin4j.PinyinHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
    FloatingActionButton fab_contact_add;

    /**RecyclerView*/
    private RecyclerView recyclerView_contacts;
    private ContactsAdapter contactsAdapter;
    private LinearLayoutManager layoutManager;

    /**room*/
    private ContactViewModel mContactViewModel;

    //Contacts list from DB
    private List<ContactsEntity> contactsNamesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        TV_contact_short_sort = findViewById(R.id.tv_contact_short_sort);
        TV_contact_short_sort.setBackgroundResource(R.color.color_gray);

        STL_contacts = findViewById(R.id.lsl_contacts);
        STL_contacts.setSortTextView(TV_contact_short_sort);
        STL_contacts.setOnTouchingLetterChangedListener(this);

        fab_contact_add = findViewById(R.id.fab_contact_add);


        contactsNamesList = new ArrayList<>();

        /**后面加上用intent获取标题功能*/
        TV_contacts_title = findViewById(R.id.TV_contacts_title);
        String diaryTitle = getIntent().getStringExtra("diaryTitle");
        if (diaryTitle == null) {
            diaryTitle = "Contacts";
        }
        TV_contacts_title.setText(diaryTitle);

        initTopicAdapter();//初始化recyclerview
        loadContacts();//载入所有的contacts到list中，并完成排序功能

        /**新增通讯录的fab监听*/
        fab_contact_add.setOnClickListener(add_contact -> {
            //contactsId is -1 because it will create new contact not get specific contact
            ContactsDetailDialogFragment contactsDetailDialogFragment =
                    ContactsDetailDialogFragment.newInstance(false,-1,"", "", topicId);
             contactsDetailDialogFragment.show(getSupportFragmentManager(), "contactsDetailDialogFragment");
        });

        /**START get contacts details from ContactsDetailDialogFragment*/
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
        /**END get contacts details from ContactsDetailDialogFragment*/
    }


    /**
     *为什么需要将所有的contacts载入到list中，因为只有通过这样，才能完成contacts的排序,进而完成根据首字母显示对应的头部
     * 28号更新，其实不用的到一共有多少数据的，getSpecificTopicIdContactCount没啥用，因为可以sizeof
     * @author home
     *@time 2021/5/26 22:06
    */
    private void loadContacts() {

        mContactViewModel.getSpecificContacts(1).observe(this, new Observer<List<Contact>>() {
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

                sortContacts();

            }
        });

        /**动态获取livedata通讯录总量， 不过并没有必要这么写*/
//        mContactViewModel.getSpecificTopicIdContactCount(1).observe(this, new Observer<Integer>() {
//            @Override
//            public void onChanged(Integer integer) { //integer就是对应topic id通讯录的总数
////              Log.d("test", "now the total contacts number is " + String.valueOf(integer));
//                contactsNamesList.clear();//每次数据改变，都要重新填充一次list列表，让新的数据加载进去
//            }
//        });

    }

    /**对列表进行排序*/
    private void sortContacts() {
        for (ContactsEntity contactsEntity : contactsNamesList) {
            String sortString = contactsEntity.getName().substring(0, 1).toUpperCase();
            sortContactsCN(contactsEntity, sortString);
        }
        Collections.sort(contactsNamesList, new LetterComparator());
    }

    /**根据中文进行排序*/
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
        final ContactsAdapter contactsAdapter = new ContactsAdapter(new ContactsAdapter.ContactDiff(),
                this, contactsNamesList, topicId);
        recyclerView_contacts.setAdapter(contactsAdapter);
        recyclerView_contacts.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_contacts.setHasFixedSize(true);
        mContactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        mContactViewModel.getSpecificContacts(1).observe(this, contacts -> {
            contactsAdapter.submitList(contacts);
        });

    }


}