package com.example.ourdiary.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ourdiary.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView iv_main_setting,iv_main_search;
    private EditText et_main_search;
    private static String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //显示日记，通讯录、Memo的rv的Fragment
        if(savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            RecyclerViewFragment fragment = new RecyclerViewFragment(this);
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