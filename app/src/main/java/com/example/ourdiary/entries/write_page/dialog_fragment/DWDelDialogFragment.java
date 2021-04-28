package com.example.ourdiary.entries.write_page.dialog_fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.ourdiary.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DWDelDialogFragment extends DialogFragment {

    EditText et_diary_write_delete_nu;
    FloatingActionButton fab_diary_delete_one;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fg_diary_write_delete_one, null);
        builder.setView(view);
        et_diary_write_delete_nu = view.findViewById(R.id.et_diary_write_delete_nu);
        fab_diary_delete_one = view.findViewById(R.id.fab_diary_delete_one);

        fab_diary_delete_one.setOnClickListener(v -> {
            Bundle result = new Bundle();
            result.putString("delete_nu", String.valueOf(et_diary_write_delete_nu.getText()));
            getParentFragmentManager().setFragmentResult("deleteOne", result);
            dismiss();//使此fragment消失
        });
        return  builder.create();
    }

}
