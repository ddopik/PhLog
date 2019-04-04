package com.example.softmills.phlog.ui.search.view.album.presenter;

import com.example.softmills.phlog.ui.search.view.album.model.Filter;

import java.util.List;
import java.util.Map;

/**
 * Created by abdalla_maged on 10/30/2018.
 */
public interface AlbumSearchPresenter {

    void getSearchFilters();

    Map<String, String> getFilter(List<Filter> filterList);

    void getAlbumSearchQuery(String s, List<Filter> filterList, String page);


 }
