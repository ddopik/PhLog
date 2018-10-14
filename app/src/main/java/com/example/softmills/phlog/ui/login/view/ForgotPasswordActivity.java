package com.example.softmills.phlog.ui.login.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.BaseActivity;

public class ForgotPasswordActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
    }



    @Override
    public void initView() {

    }

    @Override
    public void initPresenter() {

    }
    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }
}
