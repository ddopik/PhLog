package com.example.softmills.phlog.ui.photographerprofile.view.ph_saved.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.softmills.phlog.Utiltes.PrefUtils;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_saved.view.FragmentPhotoGrapherSavedPhotosView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla_maged on 9/30/2018.
 */
public class FragmentPhotoGrapherSavedPresenterImpl implements FragmentPhotoGrapherSavedPresenter {

    private static String TAG = FragmentPhotoGrapherSavedPresenterImpl.class.getSimpleName();
    private FragmentPhotoGrapherSavedPhotosView fragmentPhotoGrapherSavedPhotosView;
private Context context;

    public FragmentPhotoGrapherSavedPresenterImpl(Context context,FragmentPhotoGrapherSavedPhotosView fragmentPhotoGrapherSavedPhotosView) {
        this.fragmentPhotoGrapherSavedPhotosView = fragmentPhotoGrapherSavedPhotosView;
this.context=context;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getPhotographerSavedPhotos(String Page) {

        BaseNetworkApi.getPhotoGrapherSavedPhotos(PrefUtils.getUserToken(context), Page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(photoGrapherPhotosResponse -> {
                    Log.e(TAG, "getPhotoGrapherSavedPhotos() --->" + photoGrapherPhotosResponse.toString());
                    if (photoGrapherPhotosResponse.state.equals(BaseNetworkApi.STATUS_OK)) {
                        Log.e(TAG, "getPhotoGrapherSavedPhotos() --->" + BaseNetworkApi.STATUS_OK);
                        fragmentPhotoGrapherSavedPhotosView.showSavedPhotos(photoGrapherPhotosResponse.data.data);
                    }
                }, throwable -> {
                    try {

                        fragmentPhotoGrapherSavedPhotosView.showMessage(throwable.getMessage());
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                    }

                });

    }


}

