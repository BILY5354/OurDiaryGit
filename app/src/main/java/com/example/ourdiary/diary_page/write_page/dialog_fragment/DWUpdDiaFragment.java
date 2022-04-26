package com.example.ourdiary.diary_page.write_page.dialog_fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentResultListener;

import com.example.ourdiary.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DWUpdDiaFragment extends DialogFragment {

    /**UI*/
    TextView tv_view_nu;
    EditText et_view_title,et_view_content;
    FloatingActionButton fab_update,fab_delete_one,fab_back;

    /**用于Bundle的数据*/
    String get_title,get_content;
    int get_nu;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fg_diary_write_view_and_update, null);
        builder.setView(view);
        tv_view_nu = view.findViewById(R.id.tv_diary_write_view_and_update_nu);
        et_view_title = view.findViewById(R.id.et_diary_write_view_and_update_title);
        et_view_content = view.findViewById(R.id.et_diary_write_view_and_update_content);
        fab_back = view.findViewById(R.id.fab_fg_diary_write_and_update_back);
        fab_delete_one = view.findViewById(R.id.fab_fg_diary_write_and_update_delete_one);
        fab_update = view.findViewById(R.id.fab_fg_diary_write_and_update_find_out);

        /**获取DiaryWriteFragment的值*/
        getParentFragmentManager().setFragmentResultListener("requestSpecificDiary", this, new FragmentResultListener() {

            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                get_nu = result.getInt("bundle_specific_nu");
                get_title = result.getString("bundle_specific_title");
                get_content = result.getString("bundle_specific_content");
                Log.d("test","DWUfragment" + get_nu);
                tv_view_nu.setText("Diary " + get_nu + " Edit Page");
                et_view_title.setText(get_title);
                et_view_content.setText(get_content);
            }
        });

        fab_update.setOnClickListener(update -> {
            Bundle result = new Bundle();
            result.putInt("bundle_update_nu", get_nu);
            result.putString("bundle_update_title", String.valueOf(et_view_title.getText()));
            result.putString("bundle_update_content", String.valueOf(et_view_content.getText()));
            Log.d("test","\nready been sent:" + get_nu + et_view_title.getText() + et_view_content.getText());
            getParentFragmentManager().setFragmentResult("updateSpecificDiary", result);
            Toast.makeText(getContext(), "保存成功^-^", Toast.LENGTH_SHORT).show();
            dismiss();
        });

        //删除一个
        fab_delete_one.setOnClickListener(view_delete_one -> {
            Bundle result = new Bundle();
            result.putInt("delete_nu", get_nu);
            getParentFragmentManager().setFragmentResult("deleteOne", result);
            dismiss();
        });

        //返回
        fab_back.setOnClickListener(back -> {
            dismiss();
        });

        return  builder.create();
    }
}
