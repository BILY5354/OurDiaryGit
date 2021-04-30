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
import com.example.ourdiary.entries.write_page.dialog_fragment.DWDelDialogFragment;
import com.example.ourdiary.entries.write_page.dialog_fragment.DiaryWriteInputFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static java.lang.Character.getType;

/**
 *不能查询特定的日记DWFindDiaFragment 这个运行会闪退 不知道是不是和DWViewDiaFragment有关联
 *@author home
 *@time 2021/4/26 22:29
*/
public class DiaryWriteFragment extends Fragment {

    private DiaryActivity activity;

    /***UI**/
    FloatingActionButton fab_diary_write,fab_update,fab_delete_one,fab_delete_all,fab_diary_find_out;

    /**各Bundle的标识符，用于获取不同数据库操作的返回值**/
    String result_title,result_content,result_delete_one;
    int result_sign_del,result_specific_diary_nu;//Position是日记的序号，用于更新的

    //Room
    private DiaryViewModel mDiaryViewModel;

    //测试用    TextView tv_nu,tv_title,tv_content;

    public DiaryWriteFragment(DiaryActivity activity) {
        this.activity = activity;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //start 测试用 fra监听 用于更新界面 现在用livedata了
        getParentFragmentManager().setFragmentResultListener("requestDiaryInputKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {

                result_title = bundle.getString("bundle_title");
                result_content = bundle.getString("bundle_content");
                Diary diary = new Diary(result_title,result_content);
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
                Diary diary = new Diary(result_title,result_content);
                diary.setId(result_specific_diary_nu);
                mDiaryViewModel.updateDiaries(diary);
            }
        });

        //del one diary
        getParentFragmentManager().setFragmentResultListener("deleteOne", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                result_delete_one = bundle.getString("delete_nu");
                Diary diary = new Diary("delete title","delete content");
                diary.setId(Integer.parseInt(result_delete_one));
                mDiaryViewModel.deleteDiary(diary);
            }
        });

        //del all diary
        getParentFragmentManager().setFragmentResultListener("delete_all", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                result_sign_del = bundle.getInt("sign_del");
                if (result_sign_del == 1) {
                    mDiaryViewModel.deleteAllDiaries();
                }
            }
        });

    }

    /**
     *
     *@author home
     *@time 2021/3/20 0:05
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fg_diary_write,container,false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        tv_title = view.findViewById(R.id.tv_fg_diary_write_title);
//        tv_content = view.findViewById(R.id.tv_fg_diary_write_content);
        fab_diary_write = view.findViewById(R.id.fab_diary_write);
//        fab_diary_find_out = view.findViewById(R.id.fab_diary_find_out);
        fab_update = view.findViewById(R.id.fab_diary_update);
        fab_delete_one = view.findViewById(R.id.fab_diary_delete_one);
        fab_delete_all = view.findViewById(R.id.fab_diary_delete_all);

        //新增
        fab_diary_write.setOnClickListener(view1 -> {
            DiaryWriteInputFragment diaryWriteInputFragment = new DiaryWriteInputFragment();
            diaryWriteInputFragment.show(activity.getSupportFragmentManager(),"diaryInputDialogFragment");
        });

        //查询特定日记
        /***BUG HERE!!!!!!!!!!!!!
        fab_diary_find_out.setOnClickListener(view_find_out -> {
            //DWFindDiaFragment dwFindDiaFragment = new DWFindDiaFragment(activity);
            //dwFindDiaFragment.show(activity.getSupportFragmentManager(),"dwFindDiaFragment");
        });*/

        //修改 先再变成长按修改了
        fab_update.setOnClickListener(view_update -> {
            Diary diary = new Diary("update title","update content!");
            diary.setId(2);
            mDiaryViewModel.updateDiaries(diary);
        });

        //删除一个
        fab_delete_one.setOnClickListener(view_delete_one -> {
            DWDelDialogFragment dwDelDialogFragment = new DWDelDialogFragment();
            dwDelDialogFragment.show(activity.getSupportFragmentManager(),"dwDelDialogFragment");
        });

        //删除全部
        fab_delete_all.setOnClickListener(view_delete_all -> {
            DWDelAllDiaFragment dwDelAllDiaFragment = new DWDelAllDiaFragment();
            dwDelAllDiaFragment.show(activity.getSupportFragmentManager(),"dwDelAllDiaFragment");
        });

        //Room
        RecyclerView rv_diary = view.findViewById(R.id.rv_diary_write_view);
        final  EntriesAdapter adapter_diary = new EntriesAdapter(new EntriesAdapter.DiaryDiff(), activity,this);
        rv_diary.setAdapter(adapter_diary);
        rv_diary.setLayoutManager(new LinearLayoutManager(activity));
        mDiaryViewModel = new ViewModelProvider(activity).get(DiaryViewModel.class);
        mDiaryViewModel.getAllDiariesLive().observe(activity, diaries -> {
            adapter_diary.submitList(diaries);
        });
    }

}
