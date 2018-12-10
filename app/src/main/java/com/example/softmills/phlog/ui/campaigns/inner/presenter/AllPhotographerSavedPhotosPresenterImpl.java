package com.example.softmills.phlog.ui.campaigns.inner.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.campaigns.inner.ui.AllPhotographerSavedPhotosActivityView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla_maged On Dec,2018
 */
public class AllPhotographerSavedPhotosPresenterImpl implements AllPhotographerSavedPhotosPresenter  {
    private static String TAG = AllPhotographerSavedPhotosPresenterImpl.class.getSimpleName();
    private AllPhotographerSavedPhotosActivityView allPhotographerSavedPhotosActivityView;
    private Context context;

    public AllPhotographerSavedPhotosPresenterImpl(Context context, AllPhotographerSavedPhotosActivityView allPhotographerSavedPhotosActivityView) {
        this.allPhotographerSavedPhotosActivityView = allPhotographerSavedPhotosActivityView;
        this.context = context;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getPhotographerSavedPhotos(int Page) {
allPhotographerSavedPhotosActivityView.showSavedImageProgress(true);
        BaseNetworkApi.getPhotoGrapherSavedPhotos(Page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(photoGrapherPhotosResponse -> {
                    allPhotographerSavedPhotosActivityView.showSavedImageProgress(false);
                    allPhotographerSavedPhotosActivityView.showSavedPhotos(photoGrapherPhotosResponse.data.data);
                }, throwable -> {
                    ErrorUtils.setError(context, TAG, throwable);
                    allPhotographerSavedPhotosActivityView.showSavedImageProgress(false);
                });

    }

}
