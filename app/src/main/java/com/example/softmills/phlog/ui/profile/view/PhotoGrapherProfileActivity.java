package com.example.softmills.phlog.ui.profile.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.BaseActivity;
import com.example.softmills.phlog.ui.profile.view.campains.PhotoGrapherCampainsFragment;
import com.example.softmills.phlog.ui.profile.view.follwing.PhotoGrapherFollowingFragment;
import com.example.softmills.phlog.ui.profile.view.photos.view.PhotoGraphrtPhotosFragment;
import com.example.softmills.phlog.ui.profile.view.saved.PhotoGrapherSavedFragment;

import java.util.ArrayList;
import java.util.List;

public class PhotoGrapherProfileActivity extends BaseActivity {


    private TabLayout profileTabs;
    private ViewPager profileViewpager;
    private PhotoGrapherProfileViewPagerAdapter photoGrapherProfileViewPagerAdapter;
    private List<Fragment> profileFragmentList = new ArrayList<Fragment>();
    private List<String> profileFragmentListTitles = new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photographer_profile);
        getSupportActionBar().hide();
        initPresenter();
        initView();
        initListener();
    }


    @Override
    public void initView() {

        profileTabs = findViewById(R.id.profile_tabs);
        profileViewpager = findViewById(R.id.profile_viewpager);
        photoGrapherProfileViewPagerAdapter = new PhotoGrapherProfileViewPagerAdapter(getSupportFragmentManager(),getPhotoGrapherProfileFragment(),getPhotoGrapherProfileFragmentTitles());
        profileViewpager.setAdapter(photoGrapherProfileViewPagerAdapter);
        profileTabs.setupWithViewPager(profileViewpager);
    }

    @Override
    public void initPresenter() {

    }

    private void initListener() {

    }

    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }

    private List<Fragment> getPhotoGrapherProfileFragment() {

        this.profileFragmentList.add(new PhotoGraphrtPhotosFragment());
        this.profileFragmentList.add(new PhotoGrapherCampainsFragment());
        this.profileFragmentList.add(new PhotoGrapherFollowingFragment());
        this.profileFragmentList.add(new PhotoGrapherSavedFragment());

        return profileFragmentList;
    }

    private List<String> getPhotoGrapherProfileFragmentTitles() {

        profileFragmentListTitles.add(getResources().getString(R.string.photos));
        profileFragmentListTitles.add(getResources().getString(R.string.campains));
        profileFragmentListTitles.add(getResources().getString(R.string.following));
        profileFragmentListTitles.add(getResources().getString(R.string.saved));
        return profileFragmentListTitles;
    }

}
