package com.example.ourdiary.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.ourdiary.R;
import com.example.ourdiary.remote.data.LoginRepository;
import com.example.ourdiary.remote.ui.login.LoginResult;
import com.example.ourdiary.remote.ui.login.LoginViewModel;
import com.example.ourdiary.remote.ui.login.LoginViewModelFactory;


public class SettingActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


    }
}