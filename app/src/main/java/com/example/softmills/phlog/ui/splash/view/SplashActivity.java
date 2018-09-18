package com.example.softmills.phlog.ui.splash.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.BaseActivity;
import com.example.softmills.phlog.ui.signup.view.SignUpActivity;
import com.example.softmills.phlog.ui.welcome.view.WelcomeActivity;

public class SplashActivity extends BaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        new Handler().postDelayed(() ->{
            Intent intent=new Intent(this, WelcomeActivity.class);
            startActivity(intent);
        },3000);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initPresenter() {

    }
}
