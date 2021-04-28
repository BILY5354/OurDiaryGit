package com.example.ourdiary.entries.entries_page;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;


import com.example.ourdiary.R;
import com.example.ourdiary.entries.DiaryActivity;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class DiaryEntriesFragment extends Fragment {

    private DiaryActivity activity;
    DiaryEntriesAdapter diaryEntriesAdapter;
    ViewPager2 viewPager2;

    public DiaryEntriesFragment(DiaryActivity activity) { this.activity = activity; }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     *
     *@author home
     *@time 2021/3/20 0:05
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//原来一开始用的        View rootView = inflater.inflate(R.layout.fg_diary_entries,container,false);
        return inflater.inflate(R.layout.fg_diary_entries, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        diaryEntriesAdapter = new DiaryEntriesAdapter(this);
        viewPager2 = view.findViewById(R.id.vp_diary_entries);
        viewPager2.setAdapter(diaryEntriesAdapter);
        viewPager2.setPageTransformer(new ZoomOutPageTransformer());//设置页面轮动时的动画

        //用于显示object顶部
        TabLayout tabLayout = view.findViewById(R.id.tab_layout_diary_entries);
        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> tab.setText("OBJECT " + (position + 1))
        ).attach();
    }
}
