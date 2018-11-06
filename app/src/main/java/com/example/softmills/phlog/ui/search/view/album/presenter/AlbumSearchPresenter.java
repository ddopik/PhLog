package com.example.softmills.phlog.ui.search.view.album.presenter;

import com.example.softmills.phlog.ui.search.view.album.model.AlbumSearchData;

/**
 * Created by abdalla_maged on 10/30/2018.
 */
public interface AlbumSearchPresenter {

    void getSearchFilters();
    void getAlbumSearch(String key,int page);
}
