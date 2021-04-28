package com.example.ourdiary.init;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.ourdiary.R;
import com.example.ourdiary.main.MainActivity;

public class InitActivity extends AppCompatActivity implements InitTask.InitCallBack{

    //private  static int WELCOME_TIME = 3000;//停留3秒

    private int initTime = 2500;//3s
    private Handler initHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        initHandler = new Handler();
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(InitActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, WELCOME_TIME);*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        initHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                new InitTask(InitActivity.this, InitActivity.this).execute();
            }
        }, initTime);
    }

    @Override
    protected void onPause() {
        super.onPause();
        initHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onInitCompiled(boolean showReleaseNote) {
        Intent goMainPageIntent = new Intent(InitActivity.this,MainActivity.class);
        finish();
        InitActivity.this.startActivity(goMainPageIntent);
    }
}