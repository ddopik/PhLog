package com.example.softmills.phlog.ui.photographerprofile.view;
/**
 * Created by Abdalla_maged on 9/30/2018.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.base.BaseActivity;
import com.example.softmills.phlog.ui.photographerprofile.model.PhotoGrapherProfileData;
import com.example.softmills.phlog.ui.photographerprofile.presenter.PhotoGrapherProfileActivityPresenter;
import com.example.softmills.phlog.ui.photographerprofile.presenter.PhotoGrapherProfileActivityPresenterImpl;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_camaigns.view.FragmentPhotographerCampaigns;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_following.FragmentPhotoGrapherFollowing;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_photos.view.FragmentPhotoGrapherPhotos;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_saved.view.FragmentPhotoGrapherSaved;

import java.util.ArrayList;
import java.util.List;

public class ActivityPhotoGraphedProfile extends BaseActivity implements PhotoGrapherProfileActivityView {

    private List<Fragment> photoGrapherProfileFragmentList = new ArrayList<Fragment>();
    private List<String> photoGrapherFragmentListTitles = new ArrayList<String>();
    private ImageView userProfileImg;
    private TextView fullName, userName, photoCount, followersCount, followingCount;
    private RatingBar profileRating;
    private PhotoGrapherProfileActivityPresenter photoGrapherProfileActivityPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photographer_profile);
        getSupportActionBar().hide();
        initView();
        initPresenter();

        photoGrapherProfileActivityPresenter.getPhotoGrapherProfileData();
    }

    @Override
    public void initView() {

        fullName = findViewById(R.id.user_profile_full_name);
        userName = findViewById(R.id.user_profile_username);
        profileRating = findViewById(R.id.profile_rating);
        photoCount = findViewById(R.id.photos_val);
        followersCount = findViewById(R.id.followers_val);
        followingCount = findViewById(R.id.following_val);
        userProfileImg=findViewById(R.id.user_profile_img);

        TabLayout userProfileTabs = findViewById(R.id.photographer_profile_tabs);
        ViewPager userProfileViewpager = findViewById(R.id.photographer_profile_viewpager);
        PhotoGrapherProfileViewPagerAdapter photographerProfileViewPagerAdapter = new PhotoGrapherProfileViewPagerAdapter(getSupportFragmentManager(), getUserProfileFragment(), getUserProfileFragmentTitles());
        userProfileViewpager.setAdapter(photographerProfileViewPagerAdapter);
        userProfileTabs.setupWithViewPager(userProfileViewpager);
    }

    @Override
    public void initPresenter() {
        photoGrapherProfileActivityPresenter = new PhotoGrapherProfileActivityPresenterImpl(this);
    }

    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }

    private List<Fragment> getUserProfileFragment() {


        this.photoGrapherProfileFragmentList.add(new FragmentPhotoGrapherPhotos());
        this.photoGrapherProfileFragmentList.add(new FragmentPhotographerCampaigns());
        this.photoGrapherProfileFragmentList.add(new FragmentPhotoGrapherFollowing());
        this.photoGrapherProfileFragmentList.add(new FragmentPhotoGrapherSaved());


        return photoGrapherProfileFragmentList;
    }

    private List<String> getUserProfileFragmentTitles() {

        photoGrapherFragmentListTitles.add(getResources().getString(R.string.photos));
        photoGrapherFragmentListTitles.add(getResources().getString(R.string.campaigns));
        photoGrapherFragmentListTitles.add(getResources().getString(R.string.following));
        photoGrapherFragmentListTitles.add(getResources().getString(R.string.saved));
        return photoGrapherFragmentListTitles;
    }

    @Override
    public void showPhotoGrapherProfileData(PhotoGrapherProfileData photoGrapherProfileData) {


        fullName.setText(photoGrapherProfileData.fullName);
        userName.setText(photoGrapherProfileData.userName);
        profileRating.setRating(photoGrapherProfileData.rate.floatValue());
        photoCount.setText(photoGrapherProfileData.imagePhotographerCount);
        followersCount.setText(photoGrapherProfileData.followerCount);
        followingCount.setText(photoGrapherProfileData.followingCount);

        GlideApp.with(this)
                .load(photoGrapherProfileData.imageProfile)
                .centerCrop()
                .placeholder(R.drawable.ic_arrow_left)
                .error(R.drawable.ic_launcher_foreground)
                .into(userProfileImg);


    }

    @Override
    public void showMessage(String msg) {
        showToast(msg);
    }
}
