package com.example.ourdiary.entries.photo_page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.ourdiary.R;
import com.example.ourdiary.entries.DiaryActivity;

public class DiaryPhotoFragment extends Fragment {

    private DiaryActivity activity;

    public DiaryPhotoFragment() {

    }

    public DiaryPhotoFragment(DiaryActivity activity) {
        this.activity = activity;
    }

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
        View rootView = inflater.inflate(R.layout.fg_diary_photo,container,false);

        return rootView;
    }
}
