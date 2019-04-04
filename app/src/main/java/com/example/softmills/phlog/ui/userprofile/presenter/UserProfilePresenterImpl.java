package com.example.softmills.phlog.ui.userprofile.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.androidnetworking.error.ANError;
import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.Utiltes.Utilities;
import com.example.softmills.phlog.base.commonmodel.BaseErrorResponse;
import com.example.softmills.phlog.base.commonmodel.ErrorMessageResponse;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.userprofile.view.UserProfileActivityView;
import com.google.gson.Gson;

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
        BaseNetworkApi.getUserProfile(userID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userProfileResponse -> {
                    userProfileActivityView.viewUserData(userProfileResponse.data);
                }, throwable -> {
                    if (throwable instanceof ANError) {
                        ANError error = (ANError) throwable;
                        if (error.getErrorCode() == BaseNetworkApi.STATUS_BAD_REQUEST) {
                            ErrorMessageResponse errorMessageResponse = new Gson().fromJson(error.getErrorBody(), ErrorMessageResponse.class);
                            for (BaseErrorResponse e : errorMessageResponse.errors) {
                                if (e.code.equals(BaseNetworkApi.ERROR_NOT_FOUND)) {
                                    userProfileActivityView.showNotFoundDialog();
                                    break;
                                }
                            }
                        }
                    }
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                });
    }


    @SuppressLint("CheckResult")
    @Override
    public void getUserPhotos(String userID, int page) {



            userProfileActivityView.viewUserPhotosProgress(true);
            BaseNetworkApi.getUserProfilePhotos(userID, page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(userPhotosResponse -> {
                        userProfileActivityView.viewUserPhotosProgress(false);
                        userProfileActivityView.viewUserPhotos(userPhotosResponse.data.data);

                        if (userPhotosResponse.data.nextPageUrl != null) {
                            userProfileActivityView.setNextPageUrl(Utilities.getNextPageNumber(context, userPhotosResponse.data.nextPageUrl));

                        } else {
                            userProfileActivityView.setNextPageUrl(null);
                        }


                    }, throwable -> {
                        userProfileActivityView.viewUserPhotosProgress(false);
                        ErrorUtils.Companion.setError(context, TAG, throwable);
                    });

    }

    @SuppressLint("CheckResult")
    @Override
    public void followUser(String userId) {
        BaseNetworkApi.followUser(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(followUserResponse -> {
                    userProfileActivityView.viewUserFollowingState(true);
                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void unFollowUser(String userID) {
        BaseNetworkApi.unFollowUser(userID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(followUserResponse -> {
                    userProfileActivityView.viewUserFollowingState(false);
                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                });

    }
}
