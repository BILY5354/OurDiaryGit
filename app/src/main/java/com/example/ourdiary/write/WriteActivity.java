package com.example.ourdiary.write;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.ourdiary.R;

public class WriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}