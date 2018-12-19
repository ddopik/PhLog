package com.example.softmills.phlog.ui.photographerprofile.view.ph_photos.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.Utiltes.PrefUtils;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_photos.view.FragmentPhotoGrapherPhotosView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla_maged on 10/1/2018.
 */
public class FragmentPhotoGrapherPhotosPresenterImpl implements FragmentPhotoGrapherPhotosPresenter {
    private static String TAG = FragmentPhotoGrapherPhotosPresenterImpl.class.getSimpleName();

    private FragmentPhotoGrapherPhotosView fragmentPhotoGrapherPhotosView;
    private Context context;

    public FragmentPhotoGrapherPhotosPresenterImpl(Context context, FragmentPhotoGrapherPhotosView fragmentPhotoGrapherPhotosView) {
        this.fragmentPhotoGrapherPhotosView = fragmentPhotoGrapherPhotosView;
        this.context = context;

    }
    @SuppressLint("CheckResult")
    @Override
    public void getPhotographerPhotos(int page) {
        fragmentPhotoGrapherPhotosView.showPhotosProgress(true);
        BaseNetworkApi.getPhotoGrapherPhotos(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(photosResponse -> {
                    fragmentPhotoGrapherPhotosView.showPhotosProgress(false);
                    fragmentPhotoGrapherPhotosView.showPhotos(photosResponse.data.data);
                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                    fragmentPhotoGrapherPhotosView.showPhotosProgress(false);
                });


    }
}
