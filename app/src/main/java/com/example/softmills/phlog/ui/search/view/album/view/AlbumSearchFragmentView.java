package com.example.softmills.phlog.ui.search.view.album.view;

import com.example.softmills.phlog.ui.search.view.album.model.AlbumSearch;
import com.example.softmills.phlog.ui.search.view.album.model.AlbumSearchData;
import com.example.softmills.phlog.ui.search.view.album.model.SearchFilter;

import java.util.List;

/**
 * Created by abdalla_maged on 10/30/2018.
 */
public interface AlbumSearchFragmentView {

    void viewSearchFilters(List<SearchFilter> searchFilterList);
    void viewSearchAlbum(AlbumSearchData albumSearchData);
    void showMessage(String msg);
    void showFilterSearchProgress(boolean state);

}
