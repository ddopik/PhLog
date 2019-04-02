package com.example.softmills.phlog.ui.photographerprofile.view.ph_follow.brand.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.Utiltes.PrefUtils;
import com.example.softmills.phlog.Utiltes.Utilities;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.photographerprofile.view.ph_follow.brand.view.PhotoGrapherBrandFragmentView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla_maged on 11/11/2018.
 */
public class PhotoGrapherBrandPresenterImpl implements PhotoGrapherBrandPresenter {

    private String TAG=PhotoGrapherBrandPresenterImpl.class.getSimpleName();
    private Context context;
    private PhotoGrapherBrandFragmentView photoGrapherBrandFragmentView;
    public PhotoGrapherBrandPresenterImpl(Context context,PhotoGrapherBrandFragmentView photoGrapherBrandFragmentView){
        this.context=context;
        this.photoGrapherBrandFragmentView=photoGrapherBrandFragmentView;
    }
    @SuppressLint("CheckResult")
    @Override
    public void getFollowingBrand(String key, String page) {
        BaseNetworkApi.getProfileBrand(key,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(photographerFollowingBrandResponse -> {
                        photoGrapherBrandFragmentView.viewPhotoGrapherFollowingBrandProgress(false);
                        photoGrapherBrandFragmentView.viewPhotoGrapherFollowingBrand(photographerFollowingBrandResponse.data.data);
                    if (photographerFollowingBrandResponse.data.nextPageUrl != null) {
                        photoGrapherBrandFragmentView.setNextPageUrl(Utilities.getNextPageNumber(context, photographerFollowingBrandResponse.data.nextPageUrl));

                    } else {
                        photoGrapherBrandFragmentView.setNextPageUrl(null);
                    }


                },throwable -> {
                    photoGrapherBrandFragmentView.viewPhotoGrapherFollowingBrandProgress(false);
                });
    }
}
