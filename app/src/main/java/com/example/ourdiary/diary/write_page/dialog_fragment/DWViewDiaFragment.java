package com.example.ourdiary.diary.write_page.dialog_fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentResultListener;

import com.example.ourdiary.R;

/**
 *长按其中一篇进入浏览或点击页面
 *@author home
 *@time 2021/4/26 8:50
*/
public class DWViewDiaFragment extends DialogFragment {

    /**UI*/
    TextView tv_view_nu,tv_view_title,tv_view_content;

    /**用于Bundle的数据*/
    String get_title,get_content;
    int get_nu;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fg_diary_write_view_only, null);
        builder.setView(view);
        tv_view_nu = view.findViewById(R.id.tv_diary_write_view_only);
        tv_view_title = view.findViewById(R.id.tv_diary_write_view_only_title);
        tv_view_content = view.findViewById(R.id.tv_diary_write_view_only_content);

        /**获取DiaryWriteFragment的值*/
        getParentFragmentManager().setFragmentResultListener("requestSpecificDiary", this, new FragmentResultListener() {

            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                get_nu = result.getInt("bundle_specific_nu");
                get_title = result.getString("bundle_specific_title");
                get_content = result.getString("bundle_specific_content");
                Log.d("test",get_title + get_content);
                tv_view_nu.setText("日记" + get_nu + "页面");
                tv_view_title.setText(get_title);
                tv_view_content.setText(get_content);
            }
        });

        /**find_out 获取DiaryWriteFragment的值*/
        /*
        getParentFragmentManager().setFragmentResultListener("find_out", this, new FragmentResultListener() {

            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                get_nu = result.getInt("find_out_nu");
                Log.d("test", String.valueOf(get_nu));
                get_title = result.getString("find_out_title");
                get_content = result.getString("find_out_Content");
                //Log.d("test",get_title + get_content);
                tv_view_nu.setText("日记" + get_nu + "页面");
                tv_view_title.setText(get_title);
                tv_view_content.setText(get_content);
            }
        });*/

        return  builder.create();
    }

}
