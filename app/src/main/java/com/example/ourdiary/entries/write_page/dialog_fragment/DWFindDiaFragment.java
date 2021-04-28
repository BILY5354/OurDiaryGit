package com.example.ourdiary.entries.write_page.dialog_fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;

import com.example.ourdiary.R;
import com.example.ourdiary.db.room.diary_database.Diary;
import com.example.ourdiary.db.room.diary_database.DiaryViewModel;
import com.example.ourdiary.entries.DiaryActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 *要想运行mDiaryViewModel及mDiaryViewModel.getSpecificDiary(1).observe(activity, diaries -> 此fragment必须有构造器，传入activity，这个activity是diaryActivity
 * 不然会报null
 *@author home
 *@time 2021/4/26 21:59
*/
public class DWFindDiaFragment extends DialogFragment {

    EditText et_diary_write_find_out_nu;
    FloatingActionButton fab_diary_write_find_out_one;

    //Room
    private DiaryViewModel mDiaryViewModel;

    private DiaryActivity activity;

    public DWFindDiaFragment(DiaryActivity activity) {
        this.activity = activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fg_diary_write_view_findout, null);
        builder.setView(view);
        fab_diary_write_find_out_one = view.findViewById(R.id.fab_diary_fg_find_out);
        et_diary_write_find_out_nu = view.findViewById(R.id.et_diary_write_find_out_nu);

        Bundle result = new Bundle();
        DWViewDiaFragment dwViewDiaFragment = new DWViewDiaFragment();
        /**传递值并开启看viewFra*/
        fab_diary_write_find_out_one.setOnClickListener( find_out -> {
            int diary_nu = Integer.parseInt(String.valueOf(et_diary_write_find_out_nu.getText()));
            String get_title,get_content;
            result.putInt("find_out_nu", diary_nu);
            mDiaryViewModel = new ViewModelProvider(activity).get(DiaryViewModel.class);
            mDiaryViewModel.getSpecificDiary(diary_nu).observe(activity, diaries -> {
                Diary diary = diaries;
//测试用                Log.d("test",diary.getDiary_Title());
                result.putString("find_out_title", diary.getDiary_Title());
                result.putString("find_out_Content", diary.getDiary_Content());
                getParentFragmentManager().setFragmentResult("find_out",result);
                dwViewDiaFragment.show(getActivity().getSupportFragmentManager(),"dwViewDiaFragment");
                dismiss();
            });

        });
        return  builder.create();
    }
}
