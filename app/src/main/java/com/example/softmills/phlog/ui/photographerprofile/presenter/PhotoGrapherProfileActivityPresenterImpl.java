package com.example.softmills.phlog.ui.photographerprofile.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.softmills.phlog.R;
import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.Utiltes.PrefUtils;
import com.example.softmills.phlog.Utiltes.Utilities;
import com.example.softmills.phlog.base.commonmodel.Device;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.photographerprofile.view.PhotoGrapherProfileActivityView;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PhotoGrapherProfileActivityPresenterImpl implements PhotoGrapherProfileActivityPresenter {

    private static String TAG = PhotoGrapherProfileActivityPresenterImpl.class.getSimpleName();

    private PhotoGrapherProfileActivityView photoGrapherProfileActivityView;
    private Context context;

    public PhotoGrapherProfileActivityPresenterImpl(Context context, PhotoGrapherProfileActivityView photoGrapherProfileActivityView) {
        this.photoGrapherProfileActivityView = photoGrapherProfileActivityView;
        this.context = context;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getPhotoGrapherProfileData() {
        BaseNetworkApi.getProfileInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(profilePhotoGrapherInfoResponse -> {
                    photoGrapherProfileActivityView.showPhotoGrapherProfileData(profilePhotoGrapherInfoResponse.data);
                    PrefUtils.setCurrentUser(context,profilePhotoGrapherInfoResponse.data);
                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                });

    }

    @SuppressLint("CheckResult")
    @Override
    public void uploadPhoto(File imagePath) {
        photoGrapherProfileActivityView.showProfileProgress(true);
        BaseNetworkApi.uploadProfileImg(imagePath)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(uploadProfileImgResponse -> {
                    photoGrapherProfileActivityView.UploadProfileImgFinished(true);
                    photoGrapherProfileActivityView.showProfileProgress(false);
                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                    photoGrapherProfileActivityView.showProfileProgress(false);
                });
    }

    @Override
    public void logout(Context context) {
        PrefUtils.setLoginState(context, false);
        PrefUtils.setUserToken(context, null);
        PrefUtils.setFirebaseTokenSentToServer(context, false);
    }

    @Override
    public void logout() {
        BaseNetworkApi.updateFirebaseToken(new Device(Utilities.getDeviceName(), false, PrefUtils.getFirebaseToken(context)), PrefUtils.getUserToken(context))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response != null)
                        photoGrapherProfileActivityView.logoutSuccess();
                    else
                        photoGrapherProfileActivityView.showMessage(context.getString(R.string.error_logout));
                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                    photoGrapherProfileActivityView.showMessage(context.getString(R.string.error_logout));
                });
    }
}
