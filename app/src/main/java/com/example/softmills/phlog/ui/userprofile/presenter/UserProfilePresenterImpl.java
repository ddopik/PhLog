package com.example.softmills.phlog.ui.userprofile.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.Utiltes.PrefUtils;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.photographerprofile.model.ProfilePhotoGrapherInfoResponse;
import com.example.softmills.phlog.ui.userprofile.view.UserProfileActivityView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UserProfilePresenterImpl implements UserProfilePresenter {

    private String TAG = UserProfilePresenterImpl.class.getSimpleName();
    private Context context;
    private UserProfileActivityView userProfileActivityView;

    public UserProfilePresenterImpl(Context context, UserProfileActivityView userProfileActivityView) {
        this.context = context;
        this.userProfileActivityView = userProfileActivityView;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getUserProfileData(String userID) {
        BaseNetworkApi.getUserProfile(PrefUtils.getUserToken(context), userID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userProfileResponse -> {

                    ProfilePhotoGrapherInfoResponse userProfileData = userProfileResponse;
                    userProfileActivityView.viewUserProfileUserName(userProfileData.data.userName);
                    userProfileActivityView.viewUserProfileFullName(userProfileData.data.fullName);
                    userProfileActivityView.viewUserProfileRating(userProfileData.data.rate);
                    userProfileActivityView.viewUserProfileLevel(userProfileData.data.level);
                    userProfileActivityView.viewUserProfileProfileImg(userProfileData.data.imageProfile);
                    userProfileActivityView.viewUserProfileFollowersCount(userProfileData.data.followerCount);
                    userProfileActivityView.viewUserProfileFollowingCount(userProfileData.data.followingCount);
                    userProfileActivityView.viewUserProfilePhotosCount(userProfileData.data.photoCount);


                }, throwable -> {
                    ErrorUtils.setError(context, TAG, throwable);
                });
    }


    @SuppressLint("CheckResult")
    @Override
    public void getUserPhotos(String userID, int page) {
        userProfileActivityView.viewUserPhotosProgress(true);
        BaseNetworkApi.getUserProfilePhotos(PrefUtils.getUserToken(context), userID, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userPhotosResponse -> {
                    userProfileActivityView.viewUserPhotosProgress(false);
                    userProfileActivityView.viewUserPhotos(userPhotosResponse.data.data);
                }, throwable -> {
                    userProfileActivityView.viewUserPhotosProgress(false);
                    ErrorUtils.setError(context, TAG, throwable);
                });

    }

    @SuppressLint("CheckResult")
    @Override
    public void followUser(String userId) {
        BaseNetworkApi.followUser(PrefUtils.getUserToken(context), userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(followUserResponse -> {
                    userProfileActivityView.showMessage(context.getResources().getString(R.string.following_state) + " " + followUserResponse.data);
                }, throwable -> {
                    ErrorUtils.setError(context, TAG, throwable);
                });
    }
}
