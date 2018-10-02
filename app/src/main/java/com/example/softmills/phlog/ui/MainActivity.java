package com.example.softmills.phlog.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.BaseActivity;
import com.example.softmills.phlog.ui.campaigns.CampaignsFragment;
import com.example.softmills.phlog.ui.home.view.HomeFragment;
import com.example.softmills.phlog.ui.photographerprofile.view.PhotoGraphedProfileFragment;

/**
 * Created by abdalla_maged on 10/1/2018.
 */
public class MainActivity extends BaseActivity {

    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
        initPresenter();
        initListener();

        getSupportFragmentManager().beginTransaction().replace(R.id.view_container, new HomeFragment()).commit(); //default Screen
        bottomNavigation.setSelectedItemId(0);
    }

    @Override
    public void initView() {
        bottomNavigation = findViewById(R.id.bottom_navigation);
    }

    @Override
    public void initPresenter() {

    }

    private void initListener() {

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {

                    case R.id.navigation_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.view_container, new HomeFragment()).commit();
                        break;

                    case R.id.navigation_missions:
                        getSupportFragmentManager().beginTransaction().replace(R.id.view_container, new CampaignsFragment()).commit();
                        break;
                    case R.id.navigation_profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.view_container, new PhotoGraphedProfileFragment()).commit();
                        break;
                    default:
                        return false;
                }
                return true;
            }

        });
    }

    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }
}
