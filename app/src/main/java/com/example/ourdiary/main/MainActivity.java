package com.example.ourdiary.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.ourdiary.R;
import com.example.ourdiary.remote.data.LoginDataSource;
import com.example.ourdiary.remote.data.LoginRepository;
import com.example.ourdiary.remote.ui.login.LoginViewModel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView TV_main_statue, TV_main_intro, TV_main_username;
    private ImageView IV_main_setting, IV_main_network;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //显示日记，通讯录、Memo的rv的Fragment
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            MainPageRecyclerViewFragment fragment = new MainPageRecyclerViewFragment(this);
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }

        /**使标actionBar隐藏*/
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        //初始化界面元素
        init();

    }

    //登录操作后重新加载页面
    @Override
    protected void onResume() {
        super.onResume();
        //如果登录成功 获取登录数据 （包括是否登录成功的状态）
        LoginDataSource loginDataSource = new LoginDataSource();
        LoginRepository loginInstance = LoginRepository.getInstance(loginDataSource);
        //判断是否登录成功
        if (loginInstance.isLoggedIn()) {
            //显示用户别名与邮箱
            loginSuc();
            TV_main_intro.setText("用户名：" + loginInstance.getIntro());
            TV_main_username.setText("邮箱：" + loginInstance.getUsername());
        }

    }

    /**
     * 监听事件，三者都是置于底部的搜索图片 搜索框 设置的图标，只有设置有功能（是显示一个置于底部的fragment）
     *
     * @author home
     * @time 2021/4/10 11:21
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.IV_main_setting:
                MainSettingDialogFragment mainSettingDialogFragment = new MainSettingDialogFragment(this);
                mainSettingDialogFragment.show(getSupportFragmentManager(), "mainSettingDialogFragment");
                break;
            /*case R.id.IV_main_search:
                Toast.makeText(MainActivity.this, "Coming soon! ", Toast.LENGTH_SHORT).show();

                break;
            case R.id.ET_main_search:
                Toast.makeText(MainActivity.this, "Coming soon!! ", Toast.LENGTH_SHORT).show();
                break;*/
        }
    }


    private void init() {
        TV_main_statue = findViewById(R.id.TV_main_statue);
        TV_main_intro = findViewById(R.id.TV_main_intro);
        TV_main_username = findViewById(R.id.TV_main_username);
        IV_main_setting = findViewById(R.id.IV_main_setting);
        IV_main_network = findViewById(R.id.IV_main_network);
        IV_main_setting.setOnClickListener(this);

    }

    //登录成功页面顶部栏更新
    private void loginSuc() {
        TV_main_statue.setVisibility(View.INVISIBLE);
        TV_main_intro.setVisibility(View.VISIBLE);
        TV_main_username.setVisibility(View.VISIBLE);
        IV_main_network.setImageResource(R.drawable.ic_main_online);
    }


}