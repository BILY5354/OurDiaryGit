package com.example.ourdiary.main;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import androidx.fragment.app.DialogFragment;

import com.example.ourdiary.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * The contact is 0, diary is 1 and memo is 2, also corresponds the topic_type in TopicEntry.
 *@time 2021/6/14 9:43
*/
public class TopicDetailDialogFragment extends DialogFragment {

    private boolean isEditMode;
    private String title;
    private int topicId, topicType, topicColorCode;

    /** UI  */
    private EditText EDT_topic_detail_title;
    private Spinner SP_topic_detail_type;
    private FloatingActionButton fab_fg_topic_detail_dia_fra_back, fab_fg_topic_detail_dia_fra_update,
            fab_fg_topic_detail_dia_fra_delete_one;


    /** You must setArguments then can getArguments them in onCreateDialog */
    public static TopicDetailDialogFragment newInstance(boolean isEditMode, int topicId, String title,
                                                        int topicType, int topicColorCode) {
        Bundle args = new Bundle();
        TopicDetailDialogFragment fragment = new TopicDetailDialogFragment();
        args.putBoolean("isAddTopic", isEditMode);
        args.putInt("topicId", topicId);
        args.putString("title", title);
        args.putInt("topicType", topicType);
        args.putInt("topicColorCode", topicColorCode);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        isEditMode = getArguments().getBoolean("isAddTopic", false);
        topicId = getArguments().getInt("topicId", -1);
        title = getArguments().getString("title", "error title");
        topicType = getArguments().getInt("topicType", -1);
        topicColorCode = getArguments().getInt("topicColorCode", 0);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.getDialog().setCanceledOnTouchOutside(false);
        //This position is wrong.
        if (isEditMode && (topicId == -1 || topicType == -1)) {
            dismiss();
        }

        //start init
        View rootView = inflater.inflate(R.layout.dialog_fragment_topic_detail, container);

        EDT_topic_detail_title = rootView.findViewById(R.id.EDT_topic_detail_title);

        fab_fg_topic_detail_dia_fra_back = rootView.findViewById(R.id.fab_fg_topic_detail_dia_fra_back);
        fab_fg_topic_detail_dia_fra_update = rootView.findViewById(R.id.fab_fg_topic_detail_dia_fra_update);
        fab_fg_topic_detail_dia_fra_delete_one = rootView.findViewById(R.id.fab_fg_topic_detail_dia_fra_delete_one);

        if (isEditMode) {   //When Edit mode, don not use Spinner.
//            SP_topic_detail_type.setVisibility(View.GONE);
            EDT_topic_detail_title.setText(title);
        } else {
            fab_fg_topic_detail_dia_fra_delete_one.setVisibility(View.INVISIBLE);
            SP_topic_detail_type = rootView.findViewById(R.id.SP_topic_detail_type);
            SP_topic_detail_type.setVisibility(View.VISIBLE);
            initTopicTypeSpinner();
        }
        //end init

        //back main page
        fab_fg_topic_detail_dia_fra_back.setOnClickListener(back -> {
            dismiss();
        });

        //update one
        fab_fg_topic_detail_dia_fra_update.setOnClickListener(update -> {
            Bundle result = new Bundle();

            if (isEditMode) { //if is edit mode, update it,  else, add the new one
                if (EDT_topic_detail_title.getText().toString().length() > 0) {
                    result.putInt("topic_detail_dia_fg_update_id", topicId);
                    result.putString("topic_detail_dia_fg_update_title", EDT_topic_detail_title.getText().toString());
                    result.putInt("topic_detail_dia_fg_update_type", topicType);
                    getParentFragmentManager().setFragmentResult("topic_detail_dia_fg_update", result);
                    Toast.makeText(getContext(), "更新成功^-^", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Topic name is empty!", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (EDT_topic_detail_title.getText().toString().length() > 0) {
                    result.putString("topic_detail_dia_fg_add_title", EDT_topic_detail_title.getText().toString());
                    result.putInt("topic_detail_dia_fg_add_type", SP_topic_detail_type.getSelectedItemPosition());
                    getParentFragmentManager().setFragmentResult("topic_detail_dia_fg_add", result);
                    Toast.makeText(getContext(), "添加成功^-^", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Topic name is empty!", Toast.LENGTH_SHORT).show();
                }
            }
            dismiss();
        });

        //delete one
        fab_fg_topic_detail_dia_fra_delete_one.setOnClickListener(delete_one -> {
            Bundle result = new Bundle();
            result.putInt("topic_detail_dia_fg_delete_one_id", topicId);
            getParentFragmentManager().setFragmentResult("topic_detail_dia_fg_delete_one", result);
            dismiss();
        });

        return rootView;
    }

    /** Initialize the Spinner */
    private void initTopicTypeSpinner() {
        ArrayAdapter topicTypeAdapter = new ArrayAdapter(getActivity(), R.layout.spinner_simple_text,
                getResources().getStringArray(R.array.topic_type));
        SP_topic_detail_type.setAdapter(topicTypeAdapter);
        SP_topic_detail_type.setSelection(1);

    }


}
