package com.example.ourdiary.diary.write_page.dialog_fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.ourdiary.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DiaryWriteInputFragment extends DialogFragment {

    EditText et_title,et_content;
    FloatingActionButton fab_save;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fg_diary_write_input, null);
        builder.setView(view);
        et_title = view.findViewById(R.id.et_diary_write_title);
        et_content = view.findViewById(R.id.et_diary_write_content);
        fab_save = view.findViewById(R.id.fab_diary_input_save);


        fab_save.setOnClickListener(v -> {
//            Log.d("test","buttonsave!");
            Bundle result = new Bundle();
            result.putString("bundle_title", String.valueOf(et_title.getText()));
            result.putString("bundle_content", String.valueOf(et_content.getText()));
            getParentFragmentManager().setFragmentResult("requestDiaryInputKey", result);
            dismiss();//使此fragment消失
        });

        return  builder.create();
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
    }

}
