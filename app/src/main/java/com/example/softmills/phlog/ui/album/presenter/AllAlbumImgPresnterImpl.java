package com.example.softmills.phlog.ui.album.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.softmills.phlog.R;
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
    public void deleteImage(BaseImage baseImage) {
        allAlbumImgActivityView.viewAlbumImageListProgress(true);
        BaseNetworkApi.deleteImage(String.valueOf(baseImage.id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseStateResponse -> {
                    allAlbumImgActivityView.viewAlbumImageListProgress(false);
                    allAlbumImgActivityView.onImagePhotoGrapherDeleted(baseImage, true);
                }, throwable -> {
                    allAlbumImgActivityView.viewAlbumImageListProgress(false);
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void likePhoto(String photoId) {
        allAlbumImgActivityView.viewAlbumImageListProgress(true);
        BaseNetworkApi.likePhotoGrapherPhotoPhoto(photoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseStateResponse -> {
                    allAlbumImgActivityView.viewAlbumImageListProgress(false);
                    allAlbumImgActivityView.onImagePhotoGrapherLiked(Integer.parseInt(photoId), true);
                }, throwable -> {
                    allAlbumImgActivityView.viewAlbumImageListProgress(false);
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void unLikePhoto(String photoId) {
        allAlbumImgActivityView.viewAlbumImageListProgress(true);
        BaseNetworkApi.unlikeImage(photoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseStateResponse -> {
                    allAlbumImgActivityView.viewAlbumImageListProgress(false);
                    allAlbumImgActivityView.onImagePhotoGrapherLiked(Integer.parseInt(photoId), false);
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
    public void unSaveToProfileImage(BaseImage baseImage) {
        allAlbumImgActivityView.viewAlbumImageListProgress(true);
        BaseNetworkApi.unSavePhoto(baseImage.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(savePhotoResponse -> {
                    allAlbumImgActivityView.onImageSavedToProfile(baseImage, false);
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

        if (baseImage.photographer.isFollow) {
            BaseNetworkApi.unFollowUser(String.valueOf(baseImage.photographer.id))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(followUserResponse -> {
                        allAlbumImgActivityView.onImagePhotoGrapherFollowed(baseImage, false);
                        allAlbumImgActivityView.viewAlbumImageListProgress(false);
                    }, throwable -> {
                        ErrorUtils.Companion.setError(context, TAG, throwable);
                        allAlbumImgActivityView.viewAlbumImageListProgress(false);
                    });

        } else {
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
}
