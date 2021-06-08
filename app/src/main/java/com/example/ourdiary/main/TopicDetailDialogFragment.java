package com.example.ourdiary.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.fragment.app.DialogFragment;

import com.example.ourdiary.R;

public class TopicDetailDialogFragment extends DialogFragment {


    public static TopicDetailDialogFragment newInstance() {
        TopicDetailDialogFragment fragment = new TopicDetailDialogFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.getDialog().setCanceledOnTouchOutside(false);

        View rootView = inflater.inflate(R.layout.dialog_fragment_topic_detail, container);

        return rootView;
    }
}
