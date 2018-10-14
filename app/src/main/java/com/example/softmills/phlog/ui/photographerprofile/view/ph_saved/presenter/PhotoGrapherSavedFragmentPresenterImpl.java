package com.example.softmills.phlog.ui.photographerprofile.view.ph_saved.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.softmills.phlog.Utiltes.PrefUtils;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_saved.view.PhotoGrapherSavedPhotosFragmentView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla_maged on 9/30/2018.
 */
public class PhotoGrapherSavedFragmentPresenterImpl implements PhotoGrapherSavedFragmentPresenter {

    private static String TAG = PhotoGrapherSavedFragmentPresenterImpl.class.getSimpleName();
    private PhotoGrapherSavedPhotosFragmentView photoGrapherSavedPhotosFragmentView;
private Context context;

    public PhotoGrapherSavedFragmentPresenterImpl(Context context, PhotoGrapherSavedPhotosFragmentView photoGrapherSavedPhotosFragmentView) {
        this.photoGrapherSavedPhotosFragmentView = photoGrapherSavedPhotosFragmentView;
this.context=context;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getPhotographerSavedPhotos(int Page) {

        BaseNetworkApi.getPhotoGrapherSavedPhotos(PrefUtils.getUserToken(context), Page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(photoGrapherPhotosResponse -> {
                    Log.e(TAG, "getPhotoGrapherSavedPhotos() --->" + photoGrapherPhotosResponse.toString());
                    if (photoGrapherPhotosResponse.state.equals(BaseNetworkApi.STATUS_OK)) {
                        Log.e(TAG, "getPhotoGrapherSavedPhotos() --->" + BaseNetworkApi.STATUS_OK);
                        photoGrapherSavedPhotosFragmentView.showSavedPhotos(photoGrapherPhotosResponse.data.data);
                    }
                }, throwable -> {
                    try {

                        photoGrapherSavedPhotosFragmentView.showMessage(throwable.getMessage());
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                    }

                });

    }


}

