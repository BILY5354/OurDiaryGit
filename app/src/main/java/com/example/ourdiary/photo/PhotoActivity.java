package com.example.ourdiary.photo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.ourdiary.R;

public class PhotoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);


        /**使标actionBar隐藏*/
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}