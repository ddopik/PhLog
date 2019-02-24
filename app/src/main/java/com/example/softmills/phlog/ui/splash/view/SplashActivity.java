package com.example.softmills.phlog.ui.splash.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.Constants;
import com.example.softmills.phlog.Utiltes.Constants.MainActivityRedirectionValue;
import com.example.softmills.phlog.Utiltes.PrefUtils;
import com.example.softmills.phlog.base.BaseActivity;
import com.example.softmills.phlog.ui.MainActivity;
import com.example.softmills.phlog.ui.welcome.view.WelcomeActivity;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        new Handler().postDelayed(() -> {
            if (PrefUtils.isLoginProvided(this)) {
                Intent intent = new Intent(this, MainActivity.class);
                Bundle bundle = getIntent().getExtras();
                if (bundle != null) {
                    String payload = bundle.getString("data");
                    if (payload != null) {
                        intent.putExtra(MainActivityRedirectionValue.VALUE, MainActivityRedirectionValue.TO_POPUP);
                        intent.putExtra(MainActivityRedirectionValue.PAYLOAD, payload);
                    }
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }, 3000);
    }


    @Override
    public void initView() {


//        RotateAnimation anim = new RotateAnimation(0f, 360f, 0f, 10f);
        RotateAnimation anim = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(1200);


        final ImageView splash = findViewById(R.id.app_logo);
        splash.startAnimation(anim);


//        splash.setAnimation(null);


    }

    @Override
    public void initPresenter() {

    }
}
