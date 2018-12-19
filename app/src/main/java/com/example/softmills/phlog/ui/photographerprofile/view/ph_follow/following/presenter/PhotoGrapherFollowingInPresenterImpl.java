package com.example.softmills.phlog.ui.photographerprofile.view.ph_follow.following.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.Utiltes.PrefUtils;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_follow.following.view.PhotoGrapherFollowingFragmentView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla_maged on 10/11/2018.
 */
public class PhotoGrapherFollowingInPresenterImpl implements PhotoGrapherFollowingInPresenter {

    private String TAG = PhotoGrapherFollowingInPresenterImpl.class.getSimpleName();
    private Context context;
    private PhotoGrapherFollowingFragmentView photoGrapherFollowingFragmentView;

    public PhotoGrapherFollowingInPresenterImpl(Context context, PhotoGrapherFollowingFragmentView photoGrapherFollowingFragmentView) {
        this.context = context;
        this.photoGrapherFollowingFragmentView = photoGrapherFollowingFragmentView;

    }



    @SuppressLint("CheckResult")
    @Override

    public void getPhotoGrapherFollowingSearch(int page, String key) {
        Log.e(TAG, "getPhotoGrapherFollowingSearch() ---> key  " + key);

        photoGrapherFollowingFragmentView.viewPhotographerFollowingInProgress(true);
        BaseNetworkApi.getPhotoGrapherProfileFollowingSearch(PrefUtils.getUserToken(context), key, page)
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(photoGrapherFollowingInResponse -> {
                    photoGrapherFollowingFragmentView.viewPhotographerFollowingInProgress(false);
                    photoGrapherFollowingFragmentView.viewPhotographerFollowingSearch(photoGrapherFollowingInResponse.data.data);
                }, throwable -> {
                    photoGrapherFollowingFragmentView.viewPhotographerFollowingInProgress(false);
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                });
    }
}
