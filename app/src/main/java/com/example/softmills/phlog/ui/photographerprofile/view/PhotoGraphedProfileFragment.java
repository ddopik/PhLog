package com.example.softmills.phlog.ui.photographerprofile.view;
/**
 * Created by Abdalla_maged on 9/30/2018.
 */

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.Constants;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.base.BaseFragment;
import com.example.softmills.phlog.ui.MainActivity;
import com.example.softmills.phlog.ui.photographerprofile.model.PhotoGrapherProfileData;
import com.example.softmills.phlog.ui.photographerprofile.presenter.PhotoGrapherProfileActivityPresenter;
import com.example.softmills.phlog.ui.photographerprofile.presenter.PhotoGrapherProfileActivityPresenterImpl;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_camaigns.view.PhotographerCampaignsFragment;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_follow.following.PhotoGrapherFollowFragment;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_photos.view.EditPhotoGrapherProfileFragment;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_photos.view.PhotoGrapherPhotosFragment;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_saved.view.PhotoGrapherSavedFragment;

import java.util.ArrayList;
import java.util.List;

import static com.example.softmills.phlog.Utiltes.Constants.NavigationHelper.EDIT_PROFILE;

public class PhotoGraphedProfileFragment extends BaseFragment implements PhotoGrapherProfileActivityView {

    private static String TAG = PhotoGraphedProfileFragment.class.getSimpleName();

    private View mainView;
    private ImageView earningBtn;
    private TextView editProfileBtn;
    private List<Fragment> photoGrapherProfileFragmentList = new ArrayList<Fragment>();
    private List<String> photoGrapherFragmentListTitles = new ArrayList<String>();
    private FrameLayout photographerProfileBackgroundImg;
    private ImageView photographerProfileImg;
    private TextView photographerName, photographeruserName, photoCount, followersCount, followingCount;
    private RatingBar profileRating;
    private PhotoGrapherProfileActivityPresenter photoGrapherProfileActivityPresenter;
    private AppBarLayout mAppBarLayout;
    private ProgressBar photographerProfileProgressBar;

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
        initListener();
        photoGrapherProfileActivityPresenter.getPhotoGrapherProfileData();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initViews() {

        editProfileBtn=mainView.findViewById(R.id.edit_profile_btn);
        earningBtn=mainView.findViewById(R.id.earning_btn);
        photographerProfileBackgroundImg = mainView.findViewById(R.id.photographer_profile_background_img);
        photographerName = mainView.findViewById(R.id.photographer_profile_full_name);
        photographeruserName = mainView.findViewById(R.id.photographer_profile_username);
        profileRating = mainView.findViewById(R.id.profile_rating);
        photoCount = mainView.findViewById(R.id.photos_val);
        followersCount = mainView.findViewById(R.id.followers_val);
        followingCount = mainView.findViewById(R.id.following_val);
        photographerProfileImg = mainView.findViewById(R.id.photographer_profile_img);
        mAppBarLayout = mainView.findViewById(R.id.appBar);
        photographerProfileProgressBar = mainView.findViewById(R.id.photographer_profile_progress_bar);
        TabLayout userProfileTabs = mainView.findViewById(R.id.photographer_profile_tabs);
        ViewPager userProfileViewpager = mainView.findViewById(R.id.photographer_profile_viewpager);
        PhotoGrapherProfileViewPagerAdapter photographerProfileViewPagerAdapter = new PhotoGrapherProfileViewPagerAdapter(getChildFragmentManager(), getUserProfileFragment(), getUserProfileFragmentTitles());
        userProfileViewpager.setAdapter(photographerProfileViewPagerAdapter);
        userProfileTabs.setupWithViewPager(userProfileViewpager);

    }

    @Override
    public void initPresenter() {
        photoGrapherProfileActivityPresenter = new PhotoGrapherProfileActivityPresenterImpl(getContext(), this);
    }

    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }

    private List<Fragment> getUserProfileFragment() {

        this.photoGrapherProfileFragmentList.clear();
        PhotoGrapherPhotosFragment photoGrapherPhotosFragment = PhotoGrapherPhotosFragment.getInstance();
        PhotoGrapherSavedFragment photoGrapherSavedFragment = PhotoGrapherSavedFragment.getInstance();


        this.photoGrapherProfileFragmentList.add(photoGrapherPhotosFragment);
        this.photoGrapherProfileFragmentList.add(PhotographerCampaignsFragment.getInstance());
        this.photoGrapherProfileFragmentList.add(new PhotoGrapherFollowFragment());
        this.photoGrapherProfileFragmentList.add(photoGrapherSavedFragment);

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


        photographerName.setText(photoGrapherProfileData.fullName);
        photographeruserName.setText(photoGrapherProfileData.userName);
//        profileRating.setRating(photoGrapherProfileData.rate);
        photoCount.setText(photoGrapherProfileData.photoCount);
        followersCount.setText(photoGrapherProfileData.followerCount);

        GlideApp.with(this)
                .load(photoGrapherProfileData.imageProfile)
                .centerCrop()
                .placeholder(R.drawable.ic_check_black_24dp)
                .error(R.drawable.ic_launcher_foreground)
                .into(photographerProfileImg);



//        GlideApp.with(this).
//                load("www.google.com")
//                .apply(new RequestOptions().placeholder(R.drawable.default_photographer_profile).error(R.drawable.default_photographer_profile))
//                .into(new SimpleTarget<Drawable>() {
//                    @Override
//                    public void onResourceReady(@NonNull Drawable resource, Transition<? super Drawable> transition) {
//                        photographerProfileBackgroundImg.setBackground(resource);
//                    }
//                });




    }

    private void initListener(){
        earningBtn.setOnClickListener(view -> {
            MainActivity.navigationManger.navigate(Constants.NavigationHelper.EARNING_LIST);
        });

        editProfileBtn.setOnClickListener(view -> MainActivity.navigationManger.navigate(EDIT_PROFILE));

    }

    @Override
    public void showMessage(String msg) {
        showToast(msg);
    }

    @Override
    public void showProfileProgress(boolean state) {
        if (state) {
            photographerProfileProgressBar.setVisibility(View.VISIBLE);
        } else {
            photographerProfileProgressBar.setVisibility(View.GONE);
        }
    }
}
