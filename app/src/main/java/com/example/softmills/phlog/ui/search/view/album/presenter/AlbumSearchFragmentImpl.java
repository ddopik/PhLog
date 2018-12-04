package com.example.softmills.phlog.ui.search.view.album.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.Utiltes.PrefUtils;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.search.view.album.view.AlbumSearchFragmentView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla_maged on 10/30/2018.
 */
public class AlbumSearchFragmentImpl implements AlbumSearchPresenter {


    private String TAG = AlbumSearchFragmentImpl.class.getSimpleName();

    private Context context;
    private AlbumSearchFragmentView albumSearchFragmentView;

    public AlbumSearchFragmentImpl(Context context, AlbumSearchFragmentView albumSearchFragmentView) {
        this.context = context;
        this.albumSearchFragmentView = albumSearchFragmentView;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getSearchFilters() {
        albumSearchFragmentView.showFilterSearchProgress(true);
        BaseNetworkApi.getFilters(PrefUtils.getUserToken(context))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(searchAlbumResponse -> {
                    if (searchAlbumResponse.state.equals(BaseNetworkApi.STATUS_OK)) {
                        albumSearchFragmentView.viewSearchFilters(searchAlbumResponse.data);
                        albumSearchFragmentView.showFilterSearchProgress(false);
                    } else {
                        albumSearchFragmentView.showMessage(searchAlbumResponse.msg);
                        albumSearchFragmentView.showFilterSearchProgress(false);
                        ErrorUtils.setError(context, TAG, searchAlbumResponse.msg);
                    }
                }, throwable -> {
                    Log.e(TAG, "getFilters() --->Error " + throwable.getMessage());
                    albumSearchFragmentView.showFilterSearchProgress(false);
                    ErrorUtils.setError(context, TAG, throwable);
                });


    }

    @SuppressLint("CheckResult")
    @Override
    public void getAlbumSearch(String key,int page) {
        albumSearchFragmentView.showFilterSearchProgress(true);
        BaseNetworkApi.getSearchAlbum(PrefUtils.getUserToken(context), key, String.valueOf(page))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(albumSearchResponse -> {
                    if (albumSearchResponse.state.equals(BaseNetworkApi.STATUS_OK)) {
                        albumSearchFragmentView.viewSearchAlbum(albumSearchResponse.data.data);
                    }else {
                        ErrorUtils.setError(context, TAG, albumSearchResponse.msg);
                    }
                    albumSearchFragmentView.showFilterSearchProgress(false);
                }, throwable -> {
                    ErrorUtils.setError(context, TAG, throwable);
                    albumSearchFragmentView.showFilterSearchProgress(false);
                });
    }
}
