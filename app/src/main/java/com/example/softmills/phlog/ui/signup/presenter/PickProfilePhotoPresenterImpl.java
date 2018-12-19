package com.example.softmills.phlog.ui.signup.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.Utiltes.PrefUtils;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.MainActivity;
import com.example.softmills.phlog.ui.signup.view.PickProfilePhotoActivityView;

import java.io.File;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla_maged On Dec,2018
 */
public class PickProfilePhotoPresenterImpl implements PickProfilePhotoPresenter {
    private String TAG=PickProfilePhotoPresenterImpl.class.getSimpleName();
    private Context context;
    private PickProfilePhotoActivityView pickProfilePhotoActivityView;
    public PickProfilePhotoPresenterImpl(Context context,PickProfilePhotoActivityView pickProfilePhotoActivityView){
        this.context=context;
        this.pickProfilePhotoActivityView=pickProfilePhotoActivityView;
    }
    @SuppressLint("CheckResult")
    @Override
    public void uploadPhoto(File imagePath) {
        pickProfilePhotoActivityView.viewUploadImageProgress(true);
        BaseNetworkApi.uploadProfileImg(imagePath)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(uploadProfileImgResponse -> {
                    pickProfilePhotoActivityView.UploadProfileImgFinished(true);
                    pickProfilePhotoActivityView.viewUploadImageProgress(false);
                },throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                    pickProfilePhotoActivityView.viewUploadImageProgress(false);
                });
    }
}
