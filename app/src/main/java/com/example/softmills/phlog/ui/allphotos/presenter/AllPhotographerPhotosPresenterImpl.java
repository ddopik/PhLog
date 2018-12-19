package com.example.softmills.phlog.ui.allphotos.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.allphotos.view.AllPhotographerPhotosActivityView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla_maged On Dec,2018
 */
public class AllPhotographerPhotosPresenterImpl implements AllPhotographerPhotosPresenter {
    private static String TAG = AllPhotographerPhotosPresenterImpl.class.getSimpleName();
    private AllPhotographerPhotosActivityView allPhotographerPhotosActivityView;
    private Context context;

    public AllPhotographerPhotosPresenterImpl(Context context, AllPhotographerPhotosActivityView allPhotographerPhotosActivityView) {
        this.allPhotographerPhotosActivityView = allPhotographerPhotosActivityView;
        this.context = context;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getPhotographerPhotos(int Page) {
        allPhotographerPhotosActivityView.showImageListProgress(true);
        BaseNetworkApi.getPhotoGrapherPhotos(Page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(photoGrapherPhotosResponse -> {
                    allPhotographerPhotosActivityView.showImageListProgress(false);
                    allPhotographerPhotosActivityView.showPhotosList(photoGrapherPhotosResponse.data.data);
                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                    allPhotographerPhotosActivityView.showImageListProgress(false);
                });

    }

}
