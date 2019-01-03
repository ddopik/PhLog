package com.example.softmills.phlog.ui.album.presenter;

import android.annotation.SuppressLint;
import android.content.Context;


import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.album.view.AllAlbumImgActivityView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AllAlbumImgActivityPresenterImpl implements AllAlbumImgActivityPresenter {

    public static String TAG=AllAlbumImgActivityPresenterImpl.class.getSimpleName();

    private AllAlbumImgActivityView allAlbumImgActivityView;
    private Context context;

    public AllAlbumImgActivityPresenterImpl(AllAlbumImgActivityView allAlbumImgActivityView,Context context){
        this.context=context;
        this.allAlbumImgActivityView=allAlbumImgActivityView;

    }

    @SuppressLint("CheckResult")
    @Override
    public void getAlbumImages(int albumId, int pageNum) {
        allAlbumImgActivityView.viewAlbumImageListProgress(true);
        BaseNetworkApi.getAlbumImagesPreview( String.valueOf(albumId),String.valueOf( pageNum))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(albumPreviewImagesResponse -> {
                    allAlbumImgActivityView.viewAlbumImageList(albumPreviewImagesResponse.data.imagesList);
                    allAlbumImgActivityView.viewAlbumImageListProgress(false);
                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                    allAlbumImgActivityView.viewAlbumImageListProgress(false);
                });
    }
}
