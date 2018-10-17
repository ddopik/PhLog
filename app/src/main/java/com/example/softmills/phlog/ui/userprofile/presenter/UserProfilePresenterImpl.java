package com.example.softmills.phlog.ui.userprofile.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.PrefUtils;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.userprofile.model.UserProfileData;
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
                    if (userProfileResponse.state.equals(BaseNetworkApi.STATUS_OK)) {
                        UserProfileData userProfileData = userProfileResponse.data;
                        userProfileActivityView.viewUserProfileUserName(userProfileData.userName);
                        userProfileActivityView.viewUserProfileFullName(userProfileData.fullName);
                        userProfileActivityView.viewUserProfileRating(userProfileData.rate);
                        userProfileActivityView.viewUserProfileLevel("000");
                        userProfileActivityView.viewUserProfileProfileImg(userProfileData.imageProfile);
                        userProfileActivityView.viewUserProfileFollowersCount(userProfileData.followerCount);
                        userProfileActivityView.viewUserProfileFollowingCount(userProfileData.followingCount);
                        userProfileActivityView.viewUserProfilePhotosCount(userProfileData.imagePhotographerCount);
                    } else {
                        Log.e(TAG, "getUserProfile() ---> Errore  " + userProfileResponse.state);
                        userProfileActivityView.showMessage(userProfileResponse.data.toString());
                    }

                }, throwable -> {
                    Log.e(TAG, "getUserProfile() ---> Errore  " + throwable.getMessage());
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
                    if (userPhotosResponse.state.equals(BaseNetworkApi.STATUS_OK)) {
                        userProfileActivityView.viewUserPhotos(userPhotosResponse.data.data);
                    } else {
                        userProfileActivityView.showMessage(userPhotosResponse.state);
                        userProfileActivityView.showMessage(userPhotosResponse.data.toString());
                        Log.e(TAG, "getUserProfile() ---> Errore  " + userPhotosResponse.state);
                    }

                }, throwable -> {
                    userProfileActivityView.viewUserPhotosProgress(false);
                    Log.e(TAG, "getUserProfile() ---> Errore  " + throwable.getMessage());
                });

    }

    @SuppressLint("CheckResult")
    @Override
    public void followUser(String userId) {
        BaseNetworkApi.followUser(PrefUtils.getUserToken(context), userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(followUserResponse -> {
                    if (followUserResponse.state.equals(BaseNetworkApi.STATUS_OK)) {
                        userProfileActivityView.showMessage(context.getResources().getString(R.string.following_state) +" "+ followUserResponse.data);
                    } else {
                        userProfileActivityView.showMessage(context.getResources().getString(R.string.error_following_state));
                    }
                }, throwable -> {
                    Log.e(TAG, "followUser() ---> Error  " + throwable.getMessage());
                });
    }
}
