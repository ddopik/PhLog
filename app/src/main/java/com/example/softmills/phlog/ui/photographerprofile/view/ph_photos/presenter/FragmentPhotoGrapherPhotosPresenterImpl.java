package com.example.softmills.phlog.ui.photographerprofile.view.ph_photos.presenter;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_photos.view.FragmentPhotoGrapherPhotosView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla_maged on 10/1/2018.
 */
public class FragmentPhotoGrapherPhotosPresenterImpl implements FragmentPhotoGrapherPhotosPresenter {
    private static String TAG=FragmentPhotoGrapherPhotosPresenterImpl.class.getSimpleName();

    private FragmentPhotoGrapherPhotosView fragmentPhotoGrapherPhotosView;

    public FragmentPhotoGrapherPhotosPresenterImpl(FragmentPhotoGrapherPhotosView fragmentPhotoGrapherPhotosView) {
        this.fragmentPhotoGrapherPhotosView = fragmentPhotoGrapherPhotosView;

    }

    @SuppressLint("CheckResult")
    @Override
    public void getPhotographerPhotos(String page) {
        fragmentPhotoGrapherPhotosView.showPhotosProgress(true);
        BaseNetworkApi.getPhotoGrapherPhotos("ac99b777bf0d1e58e8e7cd8653da52f5", page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(photosResponse -> {
                    fragmentPhotoGrapherPhotosView.showPhotosProgress(false);
                    if (photosResponse.state.equals(BaseNetworkApi.STATUS_OK)) {
                        Log.e(TAG, "getPhotographerPhotos() --->" + BaseNetworkApi.STATUS_OK);
                        fragmentPhotoGrapherPhotosView.showPhotos(photosResponse.data.data);
                    }
                }, throwable -> {
                    fragmentPhotoGrapherPhotosView.showPhotosProgress(false);
                    try {
                        fragmentPhotoGrapherPhotosView.showMessage(throwable.getMessage());
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                    }

                });


    }
}
