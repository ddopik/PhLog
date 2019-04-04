package com.example.softmills.phlog.ui.photographerprofile.view.ph_saved.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.Utiltes.Utilities;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_saved.view.PhotoGrapherPhotosFragmentView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla_maged on 9/30/2018.
 */
public class PhotoGrapherSavedFragmentPresenterImpl implements PhotoGrapherSavedFragmentPresenter {

    private static String TAG = PhotoGrapherSavedFragmentPresenterImpl.class.getSimpleName();
    private PhotoGrapherPhotosFragmentView photoGrapherPhotosFragmentView;
    private Context context;

    public PhotoGrapherSavedFragmentPresenterImpl(Context context, PhotoGrapherPhotosFragmentView photoGrapherPhotosFragmentView) {
        this.photoGrapherPhotosFragmentView = photoGrapherPhotosFragmentView;
        this.context = context;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getPhotographerSavedPhotos(String Page) {
        photoGrapherPhotosFragmentView.viewPhotosLoading(true);
        BaseNetworkApi.getPhotoGrapherSavedPhotos(Page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(photoGrapherPhotosResponse -> {
                    photoGrapherPhotosFragmentView.viewPhotosLoading(false);
                    photoGrapherPhotosFragmentView.showSavedPhotos(photoGrapherPhotosResponse.data.data);
                    if (photoGrapherPhotosResponse.data.nextPageUrl != null) {
                        photoGrapherPhotosFragmentView.setNextPageUrl(Utilities.getNextPageNumber(context, photoGrapherPhotosResponse.data.nextPageUrl));
                    } else {
                        photoGrapherPhotosFragmentView.setNextPageUrl(null);
                    }
                }, throwable -> {
                    photoGrapherPhotosFragmentView.viewPhotosLoading(false);
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                });

    }


}

