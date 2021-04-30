package com.example.ourdiary.entries;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.ourdiary.R;
import com.example.ourdiary.db.room.diary_database.DiaryViewModel;
import com.example.ourdiary.entries.entries_page.DiaryEntriesFragment;
import com.example.ourdiary.entries.photo_page.fragment.PhotoGridFragment;
import com.example.ourdiary.entries.write_page.DiaryWriteFragment;

import java.util.ArrayList;
import java.util.List;


public class DiaryActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_diary_entries,btn_diary_photo_page,btn_diary_write;
    private FrameLayout fl_diary_fg;
    private RelativeLayout rl_entries_page;

    public static int currentPosition;
    private static final String KEY_CURRENT_POSITION = "com.google.samples.gridtopager.key.currentPosition";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        if(savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(KEY_CURRENT_POSITION,0);
            // Return here to prevent adding additional GridFragments when changing orientation.
            return;
        }
        fl_diary_fg = findViewById(R.id.fl_diary_fg);
        rl_entries_page = findViewById(R.id.rl_entries_page);
        initDiary();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt(KEY_CURRENT_POSITION,currentPosition);
    }

    /**
     *该监听器用于监听不同按键显示不同的fragment 分别是日记，相册和写日记
     *@author home
     *@time 2021/4/3 14:52
    */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_diary_entries:
                fl_diary_fg.setBackgroundResource(R.color.color_White);
                rl_entries_page.setBackgroundResource(R.color.topic_divider);
                FragmentTransaction transaction_entries = getSupportFragmentManager()
                        .beginTransaction();
                DiaryEntriesFragment fragment_entries = new DiaryEntriesFragment(this);
                transaction_entries.replace(R.id.fl_diary_fg,fragment_entries);
                transaction_entries.commit();
                break;
            case R.id.btn_diary_photo_page:
                fl_diary_fg.setBackgroundResource(R.drawable.iv_diary_bac_photo);
                rl_entries_page.setBackgroundResource(R.drawable.iv_diary_bac_photo);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.fl_diary_fg, new PhotoGridFragment(), PhotoGridFragment.class.getSimpleName())//一定要用replace！不然会叠加
                        .commit();
                /*FragmentTransaction transaction_photo = getSupportFragmentManager()
                        .beginTransaction();
                DiaryPhotoFragment fragment_photo = new DiaryPhotoFragment(this);
                transaction_photo.replace(R.id.fl_diary_fg,fragment_photo);
                transaction_photo.commit();*/
                break;
            case R.id.btn_diary_write:
                fl_diary_fg.setBackgroundResource(R.color.color_White);
                rl_entries_page.setBackgroundResource(R.color.topic_divider);
                FragmentTransaction transaction_write = getSupportFragmentManager()
                        .beginTransaction();
                DiaryWriteFragment fragment_write = new DiaryWriteFragment(this);
                transaction_write.replace(R.id.fl_diary_fg,fragment_write);
                transaction_write.commit();
                break;
        }
    }

    private void initDiary() {
        btn_diary_entries = findViewById(R.id.btn_diary_entries);
        btn_diary_photo_page = findViewById(R.id.btn_diary_photo_page);
        btn_diary_write = findViewById(R.id.btn_diary_write);
        btn_diary_entries.setOnClickListener(this);
        btn_diary_photo_page.setOnClickListener(this);
        btn_diary_write.setOnClickListener(this);
    }
}