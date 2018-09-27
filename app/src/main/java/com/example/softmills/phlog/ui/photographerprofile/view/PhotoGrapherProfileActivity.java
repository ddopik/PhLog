package com.example.softmills.phlog.ui.photographerprofile.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.base.BaseActivity;
import com.example.softmills.phlog.ui.userprofile.view.campaigns.view.UserCampaignsFragment;
import com.example.softmills.phlog.ui.userprofile.view.follwing.UserFollowingFragment;
import com.example.softmills.phlog.ui.userprofile.view.photos.view.UserPhotosFragment;
import com.example.softmills.phlog.ui.userprofile.view.saved.UserSavedFragment;

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

        this.profileFragmentList.add(new UserPhotosFragment());
        this.profileFragmentList.add(new UserCampaignsFragment());
        this.profileFragmentList.add(new UserFollowingFragment());
        this.profileFragmentList.add(new UserSavedFragment());

        return profileFragmentList;
    }

    private List<String> getPhotoGrapherProfileFragmentTitles() {

        profileFragmentListTitles.add(getResources().getString(R.string.photos));
        profileFragmentListTitles.add(getResources().getString(R.string.campaigns));
        profileFragmentListTitles.add(getResources().getString(R.string.following));
        profileFragmentListTitles.add(getResources().getString(R.string.saved));
        return profileFragmentListTitles;
    }

}
