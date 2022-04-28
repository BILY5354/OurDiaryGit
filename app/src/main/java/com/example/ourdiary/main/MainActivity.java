package com.example.ourdiary.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ourdiary.R;
import com.example.ourdiary.contact.ContactActivity;
import com.example.ourdiary.photo.PhotoActivity;
import com.example.ourdiary.remote.data.LoginDataSource;
import com.example.ourdiary.remote.data.LoginRepository;
import com.example.ourdiary.remote.data.model.Article;
import com.example.ourdiary.remote.data.model.LoggedInUser;
import com.example.ourdiary.write.WriteActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView TV_main_statue, TV_main_intro, TV_main_username;
    private ImageView IV_main_setting, IV_main_network,IV_main_contact, IV_main_write,IV_main_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

            //获取文章列表
            List<Article> articleList = (List<Article>) loginInstance.getArticleList().toList();
            //加载recycle view
            loadRV(loginInstance.getLoggedInUser(), articleList);
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
        Intent intent = null;
        switch (view.getId()) {
            case R.id.IV_main_setting:
                MainSettingDialogFragment mainSettingDialogFragment = new MainSettingDialogFragment(this);
                mainSettingDialogFragment.show(getSupportFragmentManager(), "mainSettingDialogFragment");
                break;
            case R.id.IV_main_contact:
                Log.d("test","通讯录被按下");
                intent = new Intent(MainActivity.this, ContactActivity.class);
                startActivity(intent);
                break;
            case R.id.IV_main_write:
                intent = new Intent(MainActivity.this, WriteActivity.class);
                startActivity(intent);
                break;
            case R.id.IV_main_photo:
                intent = new Intent(MainActivity.this, PhotoActivity.class);
                startActivity(intent);
                break;
        }
    }


    private void init() {
        TV_main_statue = findViewById(R.id.TV_main_statue);
        TV_main_intro = findViewById(R.id.TV_main_intro);
        TV_main_username = findViewById(R.id.TV_main_username);
        IV_main_setting = findViewById(R.id.IV_main_setting);
        IV_main_network = findViewById(R.id.IV_main_network);
        IV_main_contact = findViewById(R.id.IV_main_contact);
        IV_main_write = findViewById(R.id.IV_main_write);
        IV_main_photo = findViewById(R.id.IV_main_photo);
        IV_main_contact.setOnClickListener(this);
        IV_main_setting.setOnClickListener(this);
        IV_main_write.setOnClickListener(this);
        IV_main_photo.setOnClickListener(this);
    }

    //登录成功页面顶部栏更新
    private void loginSuc() {
        TV_main_statue.setVisibility(View.INVISIBLE);
        TV_main_intro.setVisibility(View.VISIBLE);
        TV_main_username.setVisibility(View.VISIBLE);
        IV_main_network.setImageResource(R.drawable.ic_main_online);
    }

    //登录成功后加载recycle view
    private void loadRV(LoggedInUser loggedInUser, List<Article> articleList) {
        //RecycleView
        RecyclerView rv = findViewById(R.id.RV_main);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        MainAdapter mainAdapter = new MainAdapter(loggedInUser, articleList);
        rv.setAdapter(mainAdapter);
    }

}