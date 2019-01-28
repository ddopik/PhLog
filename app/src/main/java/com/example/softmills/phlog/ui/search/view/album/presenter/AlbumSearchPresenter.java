package com.example.softmills.phlog.ui.search.view.album.presenter;

import com.example.softmills.phlog.ui.search.view.album.model.AlbumSearchData;
import com.example.softmills.phlog.ui.search.view.album.model.SearchFilter;

import java.util.List;

/**
 * Created by abdalla_maged on 10/30/2018.
 */
public interface AlbumSearchPresenter {

    void getSearchFilters();
    void getAlbumSearch(String key,int page);

    void getAlbumSearch(String s, List<SearchFilter> searchFilterList, int page);
}
