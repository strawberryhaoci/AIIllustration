package com.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.util.PermissionUtil;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        PermissionUtil.askPermission(this);
    }
}