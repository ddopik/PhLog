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
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.Constants;
import com.example.softmills.phlog.Utiltes.GlideApp;
import com.example.softmills.phlog.base.BaseFragment;
import com.example.softmills.phlog.base.commonmodel.Photographer;
import com.example.softmills.phlog.ui.MainActivity;
import com.example.softmills.phlog.ui.photographerprofile.presenter.PhotoGrapherProfileActivityPresenter;
import com.example.softmills.phlog.ui.photographerprofile.presenter.PhotoGrapherProfileActivityPresenterImpl;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_camaigns.view.PhotographerCampaignsFragment;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_follow.following.PhotoGrapherFollowFragment;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_photos.view.PhotoGrapherPhotosFragment;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_saved.view.PhotoGrapherSavedPhotosFragment;

import java.util.ArrayList;
import java.util.List;

import static com.example.softmills.phlog.Utiltes.Constants.NavigationHelper.EDIT_PROFILE;

public class PhotoGraphedProfileFragment extends BaseFragment implements PhotoGrapherProfileActivityView {

    private static String TAG = PhotoGraphedProfileFragment.class.getSimpleName();

    private View mainView;
    private ImageView earningBtn;
    private List<Fragment> photoGrapherProfileFragmentList = new ArrayList<Fragment>();
    private List<String> photoGrapherFragmentListTitles = new ArrayList<String>();
    private ImageView photographerProfileBackgroundImg;
    private ImageView photographerProfileImg;
    private TextView photographerName, photographeruserName, photoCount, followersCount, followingCount;
    private RatingBar profileRating;
    private PhotoGrapherProfileActivityPresenter photoGrapherProfileActivityPresenter;
    private AppBarLayout mAppBarLayout;
    private ProgressBar photographerProfileProgressBar;
    private Toolbar profileFragmentToolBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_photographer_profile, container, false);
        setHasOptionsMenu(true);
        return mainView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        initPresenter();
        initListener();
        ((MainActivity) getActivity()).setSupportActionBar(profileFragmentToolBar);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        setHasOptionsMenu(true);
        photoGrapherProfileActivityPresenter.getPhotoGrapherProfileData();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public void initViews() {
        profileFragmentToolBar = mainView.findViewById(R.id.profile_tool_bar);
//        editProfileBtn = mainView.findViewById(R.id.edit_profile_btn);
        earningBtn = mainView.findViewById(R.id.earning_btn);
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
        PhotoGrapherSavedPhotosFragment photoGrapherSavedFragment = PhotoGrapherSavedPhotosFragment.getInstance();


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
    public void showPhotoGrapherProfileData(Photographer photoGrapherProfileData) {


        photographerName.setText(photoGrapherProfileData.fullName);
        photographeruserName.setText(photoGrapherProfileData.userName);


        if (photoGrapherProfileData.rate != null)
            profileRating.setRating(Float.valueOf(photoGrapherProfileData.rate));
        if (photoGrapherProfileData.photosCount != null)
            photoCount.setText(String.valueOf(photoGrapherProfileData.photosCount));
        if (photoGrapherProfileData.followersCount != null)
            followersCount.setText(String.valueOf(photoGrapherProfileData.followersCount));

        if (photoGrapherProfileData.followingCount != null)
            followingCount.setText(String.valueOf(photoGrapherProfileData.followingCount));

        if (photoGrapherProfileData.imageProfile != null)
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

    private void initListener() {
        earningBtn.setOnClickListener(view -> {
            MainActivity.navigationManger.navigate(Constants.NavigationHelper.EARNING_LIST);
        });


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_profile_edit, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit_profile:
                MainActivity.navigationManger.navigate(EDIT_PROFILE);
                break;
            case R.id.action_logout:
                showToast("logout");
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public boolean onMenuItemClick(MenuItem menuItem) {
//
//        switch (menuItem.getItemId()) {
//            case R.id.action_edit_profile:
//                MainActivity.navigationManger.navigate(EDIT_PROFILE);
//                return true;
//            case R.id.action_logout:
//                showToast("logout");
//                return true;
//            default:
//                return false;
//        }
//    }

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
