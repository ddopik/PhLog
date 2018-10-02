package com.example.softmills.phlog.ui.photographerprofile.view.ph_photos.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

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
    public void getPhotographerPhotos(String page) {
        fragmentPhotoGrapherPhotosView.showPhotosProgress(true);
        BaseNetworkApi.getPhotoGrapherPhotos(PrefUtils.getUserToken(context), page)
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
