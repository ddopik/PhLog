package com.example.softmills.phlog.ui.search.view.album.presenter;

import com.example.softmills.phlog.ui.search.view.album.model.AlbumSearchData;
import com.example.softmills.phlog.ui.search.view.album.model.SearchFilter;

import java.util.List;

/**
 * Created by abdalla_maged on 10/30/2018.
 */
public interface AlbumSearchPresenter {

    void getSearchFilters();
    void getAlbumSearchQuery(String key, int page);

    void getAlbumSearchQuery(String s, List<SearchFilter> searchFilterList, int page);
}
