package com.example.softmills.phlog.ui.album.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.softmills.phlog.Utiltes.ErrorUtils;
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
    public AllAlbumImgPresnterImpl(Context context,AllAlbumImgActivityView allAlbumImgActivityView){
        this.context=context;
        this.allAlbumImgActivityView=allAlbumImgActivityView;
    }


    @SuppressLint("CheckResult")
    @Override
    public void likePhoto(int photoId) {
        BaseNetworkApi.likePhoto(photoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseStateResponse -> {
                    allAlbumImgActivityView.showMessage("Like");
                },throwable -> {
                    ErrorUtils.setError(context, TAG, throwable);
                });
    }



    @Override
    public void downLoadPhoto(int photoID) {

    }
}
