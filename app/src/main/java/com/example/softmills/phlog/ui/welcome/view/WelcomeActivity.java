package com.example.softmills.phlog.ui.welcome.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Button;

import com.bikomobile.circleindicatorpager.CircleIndicatorPager;
import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.BaseActivity;
import com.example.softmills.phlog.ui.home.view.HomeActivity;
import com.example.softmills.phlog.ui.login.view.LoginActivity;
import com.example.softmills.phlog.ui.signup.view.SignUpActivity;
import com.example.softmills.phlog.ui.welcome.presenter.WelcomePresenter;
import com.example.softmills.phlog.ui.welcome.presenter.WelcomeScreenImpl;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends BaseActivity implements WelcomeView {


    private static final String TAG = WelcomeActivity.class.getSimpleName();


    private PagerAdapter WelcomeSlideAdapter;
    private List<String> urlList=new ArrayList<>();
    private WelcomePresenter welcomePresenter;
    private Button signInBtn,signUpBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getSupportActionBar().hide();
        welcomePresenter = new WelcomeScreenImpl(this);
        initView();
    }

    @Override
    public void initView() {


        signInBtn=findViewById(R.id.sign_in_btn);
        signUpBtn=findViewById(R.id.sign_up_btn);


//        urlList = new ArrayList<String>();
//        urlList.add("https://iceclog.com/wp-content/uploads/2016/09/596px-Internet1.jpg");
//        urlList.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRe2VE423htE5GAa8iIEwuNf9umKaVHaz1MwpTQ-Qk_LaUHkb6r0A");
//        urlList.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRElWJrfw2VRYvsdm0M01hsjTspCy5zryS691vKlNzn2NG3mRYs");

        WelcomeSlideAdapter = new WelcomeSlideAdapter(getBaseContext(), urlList);
        ViewPager slidesViewPager = findViewById(R.id.slides_view_pager);
        slidesViewPager.setAdapter(WelcomeSlideAdapter);

        CircleIndicatorPager indicator = findViewById(R.id.circle_indicator_pager);
        indicator.setViewPager(slidesViewPager);
        welcomePresenter.getWelcomeSlidesImages();
        initListener();
    }

    @Override
    public void showWelcomeImageSlider(List<String> images) {
        urlList.clear();
        urlList.addAll(images);
        WelcomeSlideAdapter.notifyDataSetChanged();
    }

    @Override
    public void initPresenter() {

    }

    private void initListener(){
        signInBtn.setOnClickListener((view)->{
            Intent intent=new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
        signUpBtn.setOnClickListener((view)->{
            Intent intent=new Intent(this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void navigateToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
