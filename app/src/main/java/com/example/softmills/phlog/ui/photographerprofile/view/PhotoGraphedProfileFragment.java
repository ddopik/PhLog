package com.example.softmills.phlog.ui.photographerprofile.view;
/**
 * Created by Abdalla_maged on 9/30/2018.
 */

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.base.BaseFragment;
import com.example.softmills.phlog.ui.photographerprofile.model.PhotoGrapherProfileData;
import com.example.softmills.phlog.ui.photographerprofile.presenter.PhotoGrapherProfileActivityPresenter;
import com.example.softmills.phlog.ui.photographerprofile.presenter.PhotoGrapherProfileActivityPresenterImpl;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_camaigns.view.FragmentPhotographerCampaigns;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_following.FragmentPhotoGrapherFollowing;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_photos.view.FragmentPhotoGrapherPhotos;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_saved.view.FragmentPhotoGrapherSaved;

import java.util.ArrayList;
import java.util.List;

public class PhotoGraphedProfileFragment extends BaseFragment implements PhotoGrapherProfileActivityView {

    private View mainView;

    private List<Fragment> photoGrapherProfileFragmentList = new ArrayList<Fragment>();
    private List<String> photoGrapherFragmentListTitles = new ArrayList<String>();
    private ImageView userProfileImg;
    private TextView fullName, userName, photoCount, followersCount, followingCount;
    private RatingBar profileRating;
    private PhotoGrapherProfileActivityPresenter photoGrapherProfileActivityPresenter;
    private AppBarLayout mAppBarLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_photographer_profile, container, false);
        return mainView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        initViews();
        initPresenter();
        photoGrapherProfileActivityPresenter.getPhotoGrapherProfileData();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initViews() {

        fullName = mainView.findViewById(R.id.user_profile_full_name);
        userName = mainView.findViewById(R.id.user_profile_username);
        profileRating = mainView.findViewById(R.id.profile_rating);
        photoCount = mainView.findViewById(R.id.photos_val);
        followersCount = mainView.findViewById(R.id.followers_val);
        followingCount = mainView.findViewById(R.id.following_val);
        userProfileImg = mainView.findViewById(R.id.user_profile_img);
        mAppBarLayout = mainView.findViewById(R.id.appBar);
        TabLayout userProfileTabs = mainView.findViewById(R.id.photographer_profile_tabs);
        ViewPager userProfileViewpager = mainView.findViewById(R.id.photographer_profile_viewpager);
        PhotoGrapherProfileViewPagerAdapter photographerProfileViewPagerAdapter = new PhotoGrapherProfileViewPagerAdapter(getActivity().getSupportFragmentManager(), getUserProfileFragment(), getUserProfileFragmentTitles());
        userProfileViewpager.setAdapter(photographerProfileViewPagerAdapter);
        userProfileTabs.setupWithViewPager(userProfileViewpager);
    }

    @Override
    public void initPresenter() {
        photoGrapherProfileActivityPresenter = new PhotoGrapherProfileActivityPresenterImpl(getContext(),this);
    }

    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }

    private List<Fragment> getUserProfileFragment() {
        if (photoGrapherProfileFragmentList.size() == 0) {

            FragmentPhotoGrapherPhotos fragmentPhotoGrapherPhotos = FragmentPhotoGrapherPhotos.getInstance();
            FragmentPhotoGrapherSaved fragmentPhotoGrapherSaved = FragmentPhotoGrapherSaved.getInstance();

            fragmentPhotoGrapherPhotos.setOnFragmentScroll(this::setCollapseState);
            fragmentPhotoGrapherSaved.setOnFragmentScroll(this::setCollapseState);

            this.photoGrapherProfileFragmentList.add(fragmentPhotoGrapherPhotos);
            this.photoGrapherProfileFragmentList.add(new FragmentPhotographerCampaigns());
            this.photoGrapherProfileFragmentList.add(new FragmentPhotoGrapherFollowing());
            this.photoGrapherProfileFragmentList.add(fragmentPhotoGrapherSaved);
        }
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
                .placeholder(R.drawable.ic_check_black_24dp)
                .error(R.drawable.ic_launcher_foreground)
                .into(userProfileImg);


    }

    @Override
    public void showMessage(String msg) {
        showToast(msg);
    }

    private void setCollapseState(boolean state) {

        if (state) {
            mAppBarLayout.setExpanded(true);
        } else {
            mAppBarLayout.setExpanded(false);
        }
    }
}
