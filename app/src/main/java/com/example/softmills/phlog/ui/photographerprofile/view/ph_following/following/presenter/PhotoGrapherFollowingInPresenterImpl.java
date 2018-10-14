package com.example.softmills.phlog.ui.photographerprofile.view.ph_following.following.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.softmills.phlog.Utiltes.PrefUtils;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_following.following.view.PhotoGrapherFollowingFragmentView;

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
    public void getPhotoGrapherFollowing(int page) {
        photoGrapherFollowingFragmentView.viewPhotographerFollowingInProgress(true);
        BaseNetworkApi.getPhotoGrapherProfileFollowingIn(PrefUtils.getUserToken(context), page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(photoGrapherFollowingInResponse -> {
                    photoGrapherFollowingFragmentView.viewPhotographerFollowingInProgress(false);
                    if (photoGrapherFollowingInResponse.state.equals(BaseNetworkApi.STATUS_OK)) {
                        photoGrapherFollowingFragmentView.viewPhotographerFollowingIn(photoGrapherFollowingInResponse.data.data);
                    } else {
                        photoGrapherFollowingFragmentView.viewMessage(photoGrapherFollowingInResponse.state);
                        Log.e(TAG, "getPhotoGrapherFollowing() ---> Error  " + photoGrapherFollowingInResponse.state);
                    }
                }, throwable -> {
                    photoGrapherFollowingFragmentView.viewPhotographerFollowingInProgress(false);
                    Log.e(TAG, "getPhotoGrapherFollowing() ---> Error  " + throwable.getMessage());
                });
    }
}
