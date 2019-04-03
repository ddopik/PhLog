package com.example.softmills.phlog.ui.album.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.Utiltes.Utilities;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.album.view.AlbumPreviewActivityView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla_maged on 11/6/2018.
 */
public class AlbumPreviewActivityPresenterImpl implements AlbumPreviewActivityPresenter {

    private String TAG = AlbumPreviewActivityPresenterImpl.class.getSimpleName();
    private Context context;
    private AlbumPreviewActivityView albumPreviewActivityView;

    public AlbumPreviewActivityPresenterImpl(Context context, AlbumPreviewActivityView albumPreviewActivityView) {
        this.context = context;
        this.albumPreviewActivityView = albumPreviewActivityView;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getSelectedSearchAlbum(int albumID, String pageNum) {
        albumPreviewActivityView.viewAlbumPreviewProgress(true);
        BaseNetworkApi.getAlbumDetails(String.valueOf(albumID))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(albumPreviewResponse -> {
                    albumPreviewActivityView.viewAlumPreview(albumPreviewResponse.data);
                    albumPreviewActivityView.viewAlbumPreviewProgress(false);
                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                    albumPreviewActivityView.viewAlbumPreviewProgress(false);
                });


    }

    @SuppressLint("CheckResult")
    @Override
    public void getAlbumPreviewImages(int albumId, int page) {
        albumPreviewActivityView.viewAlbumPreviewProgress(true);
        BaseNetworkApi.getAlbumImagesPreview(String.valueOf(albumId), String.valueOf(page))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    albumPreviewActivityView.viewAlbumPreviewProgress(false);
                    albumPreviewActivityView.viwAlbumPreviewImages(response.data.imagesList);
                    if (response.data.nextPageUrl != null) {
                        albumPreviewActivityView.setNextPageUrl(Utilities.getNextPageNumber(context, response.data.nextPageUrl));

                    } else {
                        albumPreviewActivityView.setNextPageUrl(null);
                    }
                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                    albumPreviewActivityView.viewAlbumPreviewProgress(false);
                });
    }


}
