package com.example.ourdiary.entries.write_page;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ourdiary.R;

import com.example.ourdiary.db.room.diary_database.Diary;
import com.example.ourdiary.db.room.diary_database.DiaryViewModel;
import com.example.ourdiary.entries.DiaryActivity;
import com.example.ourdiary.entries.write_page.dialog_fragment.DWDelAllDiaFragment;
import com.example.ourdiary.entries.write_page.dialog_fragment.DiaryWriteInputFragment;
import com.example.ourdiary.main.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


/**
 *不能查询特定的日记DWFindDiaFragment 这个运行会闪退 不知道是不是和DWViewDiaFragment有关联
 *@author home
 *@time 2021/4/26 22:29
*/
public class DiaryWriteFragment extends Fragment {


    /**get_id*/
    private int topicId;
    private DiaryActivity activity;

    /***UI**/
    FloatingActionButton fab_diary_write,fab_delete_all,fab_diary_find_out;

    /**各Bundle的标识符，用于获取不同数据库操作的返回值**/
    String result_title,result_content;
    int result_sign_del,result_specific_diary_nu,result_delete_one;//Position是日记的序号，用于更新的

    //Room
    private DiaryViewModel mDiaryViewModel;

    public DiaryWriteFragment(DiaryActivity activity, int topicId) {
        this.activity = activity;
        this.topicId = topicId;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //start 测试用 fra监听 用于更新界面 现在用livedata了
        getParentFragmentManager().setFragmentResultListener("requestDiaryInputKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {

                result_title = bundle.getString("bundle_title");
                result_content = bundle.getString("bundle_content");
                Diary diary = new Diary(result_title,result_content, topicId);
                mDiaryViewModel.insertDiaries(diary);

                /*测试从另一fra获取值用
                result_title = bundle.getString("bundle_title"); result_content = bundle.getString("bundle_content"); Log.d("test",result_title);    Log.d("test",result_content);  tv_title.setText(result_title);   tv_content.setText(result_content); */
            }
        });

        //update specific diary
        getParentFragmentManager().setFragmentResultListener("updateSpecificDiary", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                result_specific_diary_nu = bundle.getInt("bundle_update_nu");
                result_title = bundle.getString("bundle_update_title");
                result_content = bundle.getString("bundle_update_content");
                Log.d("test","\nupdate return:" + result_specific_diary_nu + result_title +result_content);
                Diary diary = new Diary(result_title,result_content, topicId);
                diary.setId(result_specific_diary_nu);
                mDiaryViewModel.updateDiaries(diary);
            }
        });

        //del one diary
        getParentFragmentManager().setFragmentResultListener("deleteOne", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                result_delete_one = bundle.getInt("delete_nu");
                Diary diary = new Diary("delete title","delete content", topicId);
                diary.setId(result_delete_one);
                mDiaryViewModel.deleteDiary(diary);
            }
        });

        //del all diary
        getParentFragmentManager().setFragmentResultListener("delete_all", this, (requestKey, bundle) -> {
            result_sign_del = bundle.getInt("sign_del");
            if (result_sign_del == 1) {
                mDiaryViewModel.deleteAllDiaries();
            }
        });
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fg_diary_write,container,false);
        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        fab_diary_write = view.findViewById(R.id.fab_diary_write);
        fab_delete_all = view.findViewById(R.id.fab_diary_delete_all);

        //新增
        fab_diary_write.setOnClickListener(view1 -> {
            DiaryWriteInputFragment diaryWriteInputFragment = new DiaryWriteInputFragment();
            diaryWriteInputFragment.show(activity.getSupportFragmentManager(),"diaryInputDialogFragment");
        });

        //查询特定日记

        //删除全部
        fab_delete_all.setOnClickListener(view_delete_all -> {
            DWDelAllDiaFragment dwDelAllDiaFragment = new DWDelAllDiaFragment();
            dwDelAllDiaFragment.show(activity.getSupportFragmentManager(),"dwDelAllDiaFragment");
        });

        //Room 当新日记 或修改了日记 此fragment会更新
        RecyclerView rv_diary = view.findViewById(R.id.rv_diary_write_view);
        final  EntriesAdapter adapter_diary = new EntriesAdapter(new EntriesAdapter.DiaryDiff(),
                activity,this);
        rv_diary.setAdapter(adapter_diary);
        rv_diary.setLayoutManager(new LinearLayoutManager(activity));
        mDiaryViewModel = new ViewModelProvider(activity).get(DiaryViewModel.class);
        mDiaryViewModel.getSpecificTopicIdDiary(topicId).observe(activity, diaries -> {
            adapter_diary.submitList(diaries);
        });
    }

}
