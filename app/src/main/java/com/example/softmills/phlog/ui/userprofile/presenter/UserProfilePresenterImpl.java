package com.example.softmills.phlog.ui.userprofile.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.softmills.phlog.Utiltes.PrefUtils;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.userprofile.model.UserProfileData;
import com.example.softmills.phlog.ui.userprofile.view.UserProfileActivityView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UserProfilePresenterImpl implements UserProfilePresenter {

    private String TAG=UserProfilePresenterImpl.class.getSimpleName();
    private Context context;
    private UserProfileActivityView userProfileActivityView;

    public UserProfilePresenterImpl(Context context,UserProfileActivityView userProfileActivityView){
        this.context=context;
        this.userProfileActivityView=userProfileActivityView;
    }
    @SuppressLint("CheckResult")
    @Override
    public void getUserProfileData(String userID) {
        BaseNetworkApi.getUserProfile(PrefUtils.getUserToken(context),userID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userProfileResponse -> {
                    if(userProfileResponse.state.equals(BaseNetworkApi.STATUS_OK)){
                        UserProfileData userProfileData= userProfileResponse.data;
                        userProfileActivityView.viewUserProfileUserName(userProfileData.userName);
                        userProfileActivityView.viewUserProfileFullName(userProfileData.fullName);
                        userProfileActivityView.viewUserProfileRating(userProfileData.rate);
                        userProfileActivityView.viewUserProfileLevel("000");
                        userProfileActivityView.viewUserProfileProfileImg(userProfileData.imageProfile);
                        userProfileActivityView.viewUserProfileFollowersCount(userProfileData.followerCount);
                        userProfileActivityView.viewUserProfileFollowingCount(userProfileData.followingCount);
                        userProfileActivityView.viewUserProfilePhotosCount(userProfileData.imagePhotographerCount);
                    }

                },throwable -> {

                });
    }
}
