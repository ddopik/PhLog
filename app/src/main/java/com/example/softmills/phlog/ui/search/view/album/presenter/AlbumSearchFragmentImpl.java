package com.example.softmills.phlog.ui.search.view.album.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.softmills.phlog.Utiltes.ErrorUtils;
import com.example.softmills.phlog.Utiltes.PrefUtils;
import com.example.softmills.phlog.network.BaseNetworkApi;
import com.example.softmills.phlog.ui.search.view.album.model.FilterOption;
import com.example.softmills.phlog.ui.search.view.album.model.SearchFilter;
import com.example.softmills.phlog.ui.search.view.album.view.AlbumSearchFragmentView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        BaseNetworkApi.getFilters()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(searchAlbumResponse -> {
                    albumSearchFragmentView.viewSearchFilters(searchAlbumResponse.data);
                    albumSearchFragmentView.showFilterSearchProgress(false);
                }, throwable -> {
                    Log.e(TAG, "getFilters() --->Error " + throwable.getMessage());
                    albumSearchFragmentView.showFilterSearchProgress(false);
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                });


    }

    @SuppressLint("CheckResult")
    @Override
    public void getAlbumSearchQuery(String key, int page) {
        albumSearchFragmentView.showFilterSearchProgress(true);
        BaseNetworkApi.getSearchAlbum(key, String.valueOf(page))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(albumSearchResponse -> {
                    albumSearchFragmentView.viewSearchAlbum(albumSearchResponse.data);
                    albumSearchFragmentView.showFilterSearchProgress(false);
                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                    albumSearchFragmentView.showFilterSearchProgress(false);
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void getAlbumSearchQuery(String s, List<SearchFilter> searchFilterList, int page) {

        
        albumSearchFragmentView.showFilterSearchProgress(true);
        BaseNetworkApi.getSearchAlbum(s, getFilter(searchFilterList),String.valueOf(page))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(albumSearchResponse -> {
                    albumSearchFragmentView.viewSearchAlbum(albumSearchResponse.data);
                    albumSearchFragmentView.showFilterSearchProgress(false);
                }, throwable -> {
                    ErrorUtils.Companion.setError(context, TAG, throwable);
                    albumSearchFragmentView.showFilterSearchProgress(false);
                });
    }

    @Override
    public Map<String,String> getFilter(List<SearchFilter> searchFilterList){
        int filterCount=0;
        Map<String,String> filtersMap=new HashMap<String, String>();
        for (int i=0;i<searchFilterList.size();i++){
            for (int x=0;x<searchFilterList.get(i).options.size();x++){
                if (searchFilterList.get(i).options.get(x).isSelected) {
                    filtersMap.put("filter["+filterCount+"]",searchFilterList.get(i).options.get(x).id.toString());
                    filterCount ++;
                }
            }

        }
        return filtersMap;
    }



}
