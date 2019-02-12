package com.example.softmills.phlog.ui.album.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.base.commonmodel.BaseImage;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.album.view.AllAlbumImgActivityView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.facebook.GraphRequest.TAG;

/**
 * Created by abdalla_maged On Dec,2018
 */
public class AllAlbumImgPresnterImpl implements AllAlbumImgPresnter {

    private Context context;
    public AllAlbumImgActivityView allAlbumImgActivityView;

    public AllAlbumImgPresnterImpl(Context context, AllAlbumImgActivityView allAlbumImgActivityView) {
        this.context = context;
        this.allAlbumImgActivityView = allAlbumImgActivityView;
    }


    @SuppressLint("CheckResult")
    @Override
    public void likePhoto(String photoId) {
        allAlbumImgActivityView.viewAlbumImageListProgress(true);
        BaseNetworkApi.likePhoto(photoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseStateResponse -> {
                    allAlbumImgActivityView.viewAlbumImageListProgress(false);
                    allAlbumImgActivityView.showMessage("Like");
                }, throwable -> {
                    allAlbumImgActivityView.viewAlbumImageListProgress(false);
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                });
    }


    @SuppressLint("CheckResult")
    @Override
    public void saveToProfileImage(BaseImage baseImage) {
        allAlbumImgActivityView.viewAlbumImageListProgress(true);
        BaseNetworkApi.savePhoto(baseImage.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(savePhotoResponse -> {
                    allAlbumImgActivityView.onImageSavedToProfile(baseImage, true);
                    allAlbumImgActivityView.viewAlbumImageListProgress(false);
                }, throwable -> {
                    allAlbumImgActivityView.viewAlbumImageListProgress(false);
                    ErrorUtils.Companion.setError(context, TAG, throwable);

                });


    }

    @SuppressLint("CheckResult")
    @Override
    public void followImagePhotoGrapher(BaseImage baseImage) {
        allAlbumImgActivityView.viewAlbumImageListProgress(true);

        BaseNetworkApi.followUser(String.valueOf(baseImage.photographer.id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(followUserResponse -> {
                    allAlbumImgActivityView.onImagePhotoGrapherFollowed(baseImage, true);
                    allAlbumImgActivityView.viewAlbumImageListProgress(false);
                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                    allAlbumImgActivityView.viewAlbumImageListProgress(false);
                });

    }
}
