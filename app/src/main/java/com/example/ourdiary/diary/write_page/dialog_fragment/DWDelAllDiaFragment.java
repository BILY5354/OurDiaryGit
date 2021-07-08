package com.example.ourdiary.diary.write_page.dialog_fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.ourdiary.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DWDelAllDiaFragment extends DialogFragment {

    FloatingActionButton fab_diary_delete_all_sure,fab_diary_delete_all_back;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fg_diary_write_delete_all, null);
        builder.setView(view);
        fab_diary_delete_all_sure = view.findViewById(R.id.fab_diary_delete_all_sure);
        fab_diary_delete_all_back = view.findViewById(R.id.fab_diary_delete_all_back);


        fab_diary_delete_all_sure.setOnClickListener(sure_delete -> {
            Bundle result = new Bundle();
            result.putInt("sign_del", 1);
            getParentFragmentManager().setFragmentResult("delete_all", result);
            dismiss();//使此fragment消失
        });
        fab_diary_delete_all_back.setOnClickListener(back -> {
            Bundle result = new Bundle();
            result.putInt("sign_del", 0);
            getParentFragmentManager().setFragmentResult("delete_all", result);
            dismiss();//使此fragment消失
        });
        return  builder.create();
    }

}
