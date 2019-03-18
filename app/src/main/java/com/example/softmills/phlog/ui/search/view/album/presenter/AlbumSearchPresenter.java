package com.example.softmills.phlog.ui.search.view.album.presenter;

import com.example.softmills.phlog.ui.search.view.album.model.AlbumSearchData;
import com.example.softmills.phlog.ui.search.view.album.model.SearchFilter;

import java.util.List;
import java.util.Map;

/**
 * Created by abdalla_maged on 10/30/2018.
 */
public interface AlbumSearchPresenter {

    void getSearchFilters();
    void getAlbumSearchQuery(String key, int page);
     Map<String,String> getFilter(List<SearchFilter> searchFilterList);
    void getAlbumSearchQuery(String s, List<SearchFilter> searchFilterList, int page);
}
