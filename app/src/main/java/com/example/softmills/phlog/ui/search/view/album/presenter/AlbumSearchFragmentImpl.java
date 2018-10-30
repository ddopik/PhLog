package com.example.softmills.phlog.ui.search.view.album.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

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
        albumSearchFragmentView.showAlbumSearchProgress(true);
        BaseNetworkApi.getSearchFilters(PrefUtils.getUserToken(context))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(searchAlbumFilterResponse -> {
                    if (searchAlbumFilterResponse.state.equals(BaseNetworkApi.STATUS_OK)) {
                        albumSearchFragmentView.viewSearchFilters(searchAlbumFilterResponse.data);

                    } else {
                        albumSearchFragmentView.showMessage(searchAlbumFilterResponse.msg);
                    }
                }, throwable -> {
                    Log.e(TAG, "getSearchFilters() --->Error " + throwable.getMessage());
                });
        albumSearchFragmentView.showAlbumSearchProgress(false);

    }

}
