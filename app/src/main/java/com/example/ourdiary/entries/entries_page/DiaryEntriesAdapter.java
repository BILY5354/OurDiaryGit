package com.example.ourdiary.entries.entries_page;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class DiaryEntriesAdapter extends FragmentStateAdapter {

    public DiaryEntriesAdapter(Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Return a NEW fragment instance in createFragment(int)
        Fragment fragment = new DiaryEntriesObjectFragment();
        Bundle args = new Bundle();
        // Our object is just an integer :-P
        args.putInt(String.valueOf(DiaryEntriesObjectFragment.COL_OBJECT), position);
        args.putInt(DiaryEntriesObjectFragment.ARG_OBJECT, position + 1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 6;
    }
}
