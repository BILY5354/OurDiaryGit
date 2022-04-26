package com.example.ourdiary.diary_page.entries_page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ourdiary.R;

import static com.example.ourdiary.diary_page.entries_page.ColorData.COLOR_DATA;
import static com.example.ourdiary.diary_page.entries_page.ImageData.IMAGE_DATA;

//这个类的实例是一个单独的片段
//对象在我们的集合。
/**
 *vp显示的东西，更新并插入
 *@author home
 *@time 2021/4/4 16:15
*/
public class DiaryEntriesObjectFragment extends Fragment {

    public static final String ARG_OBJECT = "object";
    public static int COL_OBJECT = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.diary_entries_fg_collection_object, container, false);
    }

    /**
     *设置显示的东西  下标统一用COL_OBJECT
     *@author home
     *@time 2021/4/4 21:20
    */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        ((LinearLayout) view.findViewById(R.id.ll_diary_entries_object)).
                setBackgroundResource(COLOR_DATA[args.getInt(String.valueOf(COL_OBJECT))]);
        ((TextView) view.findViewById(R.id.tv_diary_entries_object))
                .setText(Integer.toString(args.getInt(ARG_OBJECT)));
        ((TextView) view.findViewById(R.id.tv_diary_entries_obj_day))
                .setText(Integer.toString(args.getInt(ARG_OBJECT ) + 4) );
        ((ImageView) view.findViewById(R.id.iv_diary_entries_obj)).
                setImageResource(IMAGE_DATA[args.getInt(String.valueOf(COL_OBJECT))]);
    }
}
