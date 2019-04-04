package com.example.softmills.phlog.ui.photographerprofile.view;
/**
 * Created by Abdalla_maged on 9/30/2018.
 */

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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

import com.bumptech.glide.request.RequestOptions;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.Constants;
import com.example.softmills.phlog.Utiltes.ErrorUtils;
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.example.softmills.phlog.Utiltes.Constants.NavigationHelper.EDIT_PROFILE;
import static com.example.softmills.phlog.Utiltes.Constants.REQUEST_CODE_CAMERA;
import static com.example.softmills.phlog.Utiltes.Constants.REQUEST_CODE_GALLERY;

public class PhotoGraphedProfileFragment extends BaseFragment implements PhotoGrapherProfileActivityView {

    private static String TAG = PhotoGraphedProfileFragment.class.getSimpleName();

    private View mainView;
    private ImageView earningBtn;
    private List<Fragment> photoGrapherProfileFragmentList = new ArrayList<Fragment>();
    private List<String> photoGrapherFragmentListTitles = new ArrayList<String>();
    private ImageView photographerProfileBackgroundImg;
    private ImageView photographerProfileImg;
    private TextView photographerName, photographeruserName, photoCount, followersCount, followingCount, level;
    private RatingBar profileRating;
    private PhotoGrapherProfileActivityPresenter photoGrapherProfileActivityPresenter;
    private ProgressBar photographerProfileProgressBar;
    private Toolbar profileFragmentToolBar;
    private Photographer photographer;

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
        initPresenter();
        initViews();
        initListener();
        if (getActivity() != null) {
            ((MainActivity) getActivity()).setSupportActionBar(profileFragmentToolBar);
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
            setHasOptionsMenu(true);
        }

    }


    @Override
    public void initViews() {
        profileFragmentToolBar = mainView.findViewById(R.id.profile_tool_bar);
        earningBtn = mainView.findViewById(R.id.earning_btn);
        photographerProfileBackgroundImg = mainView.findViewById(R.id.photographer_profile_background_img);
        photographerName = mainView.findViewById(R.id.photographer_profile_full_name);
        photographeruserName = mainView.findViewById(R.id.photographer_profile_username);
        profileRating = mainView.findViewById(R.id.profile_rating);
        photoCount = mainView.findViewById(R.id.photos_val);
        followersCount = mainView.findViewById(R.id.followers_val);
        followingCount = mainView.findViewById(R.id.following_val);
        photographerProfileImg = mainView.findViewById(R.id.photographer_profile_img);
        photographerProfileProgressBar = mainView.findViewById(R.id.photographer_profile_progress_bar);
        TabLayout userProfileTabs = mainView.findViewById(R.id.photographer_profile_tabs);
        ViewPager userProfileViewpager = mainView.findViewById(R.id.photographer_profile_viewpager);
//        userProfileViewpager.setOffscreenPageLimit(1);
        PhotoGrapherProfileViewPagerAdapter photographerProfileViewPagerAdapter = new PhotoGrapherProfileViewPagerAdapter(getChildFragmentManager(), getUserProfileFragment(), getUserProfileFragmentTitles());
        userProfileViewpager.setOffscreenPageLimit(3);
        userProfileViewpager.setAdapter(photographerProfileViewPagerAdapter);
        userProfileTabs.setupWithViewPager(userProfileViewpager);

        level = mainView.findViewById(R.id.photographer_level);
    }

    @Override
    public void initPresenter() {
        photoGrapherProfileActivityPresenter = new PhotoGrapherProfileActivityPresenterImpl(getContext(), this);
    }

    @Override
    public void onResume() {
        super.onResume();
        photoGrapherProfileActivityPresenter.getPhotoGrapherProfileData();
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

        this.photographer = photoGrapherProfileData;
        photographerName.setText(photoGrapherProfileData.fullName);
        photographeruserName.setText(String.format("@%1$s", photographer.userName));


        profileRating.setRating(photoGrapherProfileData.rate);
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
                    .apply(RequestOptions.circleCropTransform())
                    .placeholder(R.drawable.default_place_holder)
                    .error(R.drawable.default_error_img)
                    .into(photographerProfileImg);
        if (photographer.level != null)
            level.setText(photographer.level);
        GlideApp.with(this).
                load(photographer.imageCover)
                .apply(new RequestOptions().placeholder(R.drawable.default_photographer_profile).error(R.drawable.default_photographer_profile))
                .into(photographerProfileBackgroundImg);


    }

    private void initListener() {
        earningBtn.setOnClickListener(view -> {
            ((MainActivity) getActivity()).navigationManger.navigate(Constants.NavigationHelper.EARNING_LIST);
        });

//        photographerProfileImg.setOnClickListener(v -> {
//            openPickerDialog();
//        });
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
                ((MainActivity) getActivity()).navigationManger.navigate(EDIT_PROFILE);
                break;
            case R.id.action_logout:
                new AlertDialog.Builder(getContext()).setTitle(R.string.logout_confirmation)
                        .setCancelable(true)
                        .setItems(new CharSequence[]{getString(R.string.yes), getString(R.string.no)}
                                , (dialog, which) -> {
                                    switch (which) {
                                        case 0:
                                            photoGrapherProfileActivityPresenter.logout();
                                            dialog.dismiss();
                                            break;
                                        case 1:
                                            dialog.dismiss();
                                            break;
                                    }
                                }).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
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


    private void openPickerDialog() {
        CharSequence photoChooserOptions[] = new CharSequence[]{getResources().getString(R.string.general_photo_chooser_camera), getResources().getString(R.string.general_photo_chooser_gallery)};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getResources().getString(R.string.general_photo_chooser_title));
        builder.setItems(photoChooserOptions, (dialog, option) -> {
            if (option == 0) {
                RequestCameraPermutations();
            } else if (option == 1) {
                requestGalleryPermutations();
            }
        }).show();
    }


    @AfterPermissionGranted(REQUEST_CODE_CAMERA)
    private void RequestCameraPermutations() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(getContext(), perms)) {

            ImagePicker.cameraOnly().start(this);


        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.camera_and_location_rationale),
                    REQUEST_CODE_CAMERA, perms);
        }

    }

    @AfterPermissionGranted(REQUEST_CODE_GALLERY)
    private void requestGalleryPermutations() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

        if (EasyPermissions.hasPermissions(getContext(), perms)) {
            ImagePicker.create(this)
                    .returnMode(ReturnMode.ALL) // set whether pick and / or camera action should return immediate result or not.
                    .single() // single mode
                    .showCamera(false)
                    .theme(R.style.AppTheme)
                    .start();
        }
        // Already have permission

        else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.camera_and_location_rationale),
                    REQUEST_CODE_GALLERY, perms);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            //Header Img
//            GlideApp.with(getContext())
//                    .load(ImagePicker.getFirstImageOrNull(data).getPath())
//                    .error(R.drawable.ic_launcher_foreground)
//                    .override(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
//                    .apply(RequestOptions.circleCropTransform())
//                    .into(photographerProfileImg);
            photoGrapherProfileActivityPresenter.uploadPhoto(new File(ImagePicker.getFirstImageOrNull(data).getPath()));
        }
        super.onActivityResult(requestCode, resultCode, data);

    }


    // todo image Url should be Passed Here
    @Override
    public void UploadProfileImgFinished(Boolean state) {
        showMessage(state.toString());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void logoutSuccess() {
        photoGrapherProfileActivityPresenter.logout(getContext());
        ((MainActivity) getActivity()).navigationManger.navigate(Constants.NavigationHelper.LOGOUT);
    }
}
