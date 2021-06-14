package com.example.ourdiary.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ourdiary.R;
import com.example.ourdiary.db.room.topic_database.TopicEntry;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView iv_main_setting,iv_main_search;
    private EditText et_main_search;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //显示日记，通讯录、Memo的rv的Fragment
        if(savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            MainPageRecyclerViewFragment fragment = new MainPageRecyclerViewFragment(this);
            transaction.replace(R.id.sample_content_fragment,fragment);
            transaction.commit();
        }

        /**使标actionBar隐藏*/
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        //界面初始化的内容（位于底部的三）
        iv_main_setting = findViewById(R.id.IV_main_setting);
        iv_main_search = findViewById(R.id.iv_main_search);
        et_main_search = findViewById(R.id.et_main_search);
        iv_main_setting.setOnClickListener(this);
        iv_main_search.setOnClickListener(this);
        et_main_search.setOnClickListener(this);


//        TopicEntry topicEntry = new TopicEntry("second", 1, null, null, null);
//        TopicOrder topicOrder = new TopicOrder(2,1);
//        mTopicViewModel.insertTopicEntry(topicEntry);
//        mTopicViewModel.insertTopicOrder(topicOrder);
//        mTopicViewModel.getAllTopicEntriesLive().observe(this, new Observer<List<TopicEntry>>() {
//            @Override
//            public void onChanged(List<TopicEntry> topicEntries) {
//                for (int i = 0; i < topicEntries.size(); i++) {
//                    Log.d("test", topicEntries.get(i).getTopic_name());
//                }
//            }
//        });
//        mTopicViewModel.getAllTopicEntryAndOrder().observe(this, new Observer<List<TopicEntryAndOrder>>() {
//            @Override
//            public void onChanged(List<TopicEntryAndOrder> topicEntryAndOrders) {
//                Log.d("test", String.valueOf(topicEntryAndOrders.size()));
//                for (int i = 0; i < topicEntryAndOrders.size(); i++) {
//                    Log.d("test","entry is:\t" + String.valueOf(topicEntryAndOrders.get(i).getTopicEntry()) +
//                            "order is:\t" + topicEntryAndOrders.get(i).getTopicOrders());
//                }
//            }
//        });
    }


    /**
     *监听事件，三者都是置于底部的搜索图片 搜索框 设置的图标，只有设置有功能（是显示一个置于底部的fragment）
     *@author home
     *@time 2021/4/10 11:21
    */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.IV_main_setting:
                MainSettingDialogFragment mainSettingDialogFragment = new MainSettingDialogFragment(this);
                mainSettingDialogFragment.show(getSupportFragmentManager(),"mainSettingDialogFragment");
                break;
            case R.id.iv_main_search:
                Toast.makeText(MainActivity.this,"Coming soon! ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.et_main_search:
                Toast.makeText(MainActivity.this,"Coming soon!! ", Toast.LENGTH_SHORT).show();
                break;
        }
    }


}