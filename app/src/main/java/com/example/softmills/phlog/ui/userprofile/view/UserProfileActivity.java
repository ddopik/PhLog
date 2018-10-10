package com.example.softmills.phlog.ui.userprofile.view;
/**
 * Created by Abdalla_maged on 9/30/2018.
 */
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

public class UserProfileActivity extends BaseActivity {


    private List<Fragment> userProfileFragmentList = new ArrayList<Fragment>();
    private List<String> userProfileFragmentListTitles = new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        initView();
        initPresenter();
    }

    @Override
    public void initView() {
        TabLayout userProfileTabs = findViewById(R.id.photographer_profile_tabs);
        ViewPager userProfileViewpager = findViewById(R.id.photographer_profile_viewpager);
        UserProfileViewPagerAdapter userProfileViewPagerAdapter = new UserProfileViewPagerAdapter(getSupportFragmentManager(), getUserProfileFragment(), getUserProfileFragmentTitles());

        userProfileViewpager.setAdapter(userProfileViewPagerAdapter);
        userProfileTabs.setupWithViewPager(userProfileViewpager);
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }

    private List<Fragment> getUserProfileFragment() {

        this.userProfileFragmentList.add(new UserPhotosFragment());
        this.userProfileFragmentList.add(new UserCampaignsFragment());
        this.userProfileFragmentList.add(new UserFollowingFragment());
        this.userProfileFragmentList.add(new UserSavedFragment());

        return userProfileFragmentList;
    }

    private List<String> getUserProfileFragmentTitles() {

        userProfileFragmentListTitles.add(getResources().getString(R.string.photos));
        userProfileFragmentListTitles.add(getResources().getString(R.string.campaigns));
        userProfileFragmentListTitles.add(getResources().getString(R.string.following));
        userProfileFragmentListTitles.add(getResources().getString(R.string.saved));
        return userProfileFragmentListTitles;
    }

}
