package com.example.ourdiary.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import androidx.fragment.app.DialogFragment;

import com.example.ourdiary.R;

/**
 * The contact is 0, diary is 1 and memo is 2, also corresponds the topic_type in TopicEntry.
 *@time 2021/6/14 9:43
*/
public class TopicDetailDialogFragment extends DialogFragment implements View.OnClickListener {

    /** UI  */
    private EditText EDT_topic_detail_title;
    private Spinner SP_topic_detail_type;
    private Button But_topic_detail_ok, But_topic_detail_cancel;


    public static TopicDetailDialogFragment newInstance() {
        TopicDetailDialogFragment fragment = new TopicDetailDialogFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.getDialog().setCanceledOnTouchOutside(false);

        View rootView = inflater.inflate(R.layout.dialog_fragment_topic_detail, container);

        EDT_topic_detail_title = rootView.findViewById(R.id.EDT_topic_detail_title);

        But_topic_detail_cancel = rootView.findViewById(R.id.But_topic_detail_cancel);
        But_topic_detail_cancel.setOnClickListener(this);
        But_topic_detail_ok = rootView.findViewById(R.id.But_topic_detail_ok);
        But_topic_detail_ok.setOnClickListener(this);

        SP_topic_detail_type = rootView.findViewById(R.id.SP_topic_detail_type);
        initTopicTypeSpinner();
        return rootView;
    }

    /** Initialize the Spinner */
    private void initTopicTypeSpinner() {
        ArrayAdapter topicTypeAdapter = new ArrayAdapter(getActivity(), R.layout.spinner_simple_text,
                getResources().getStringArray(R.array.topic_type));
        SP_topic_detail_type.setAdapter(topicTypeAdapter);
        SP_topic_detail_type.setSelection(1);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.But_topic_detail_ok:
                if (EDT_topic_detail_title.getText().toString().length() > 0) {
                    Log.d("test", "Your choose is\t" + String.valueOf(SP_topic_detail_type.getSelectedItemPosition()));
                    dismiss();
                } else {
                    Toast.makeText(getActivity(), "Topic name is empty!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.But_topic_detail_cancel:
                dismiss();
                break;
        }
    }
}
