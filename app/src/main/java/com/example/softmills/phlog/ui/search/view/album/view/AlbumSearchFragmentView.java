package com.example.softmills.phlog.ui.search.view.album.view;

import com.example.softmills.phlog.ui.search.view.album.model.AlbumSearchData;
import com.example.softmills.phlog.ui.search.view.album.model.Filter;

import java.util.List;

/**
 * Created by abdalla_maged on 10/30/2018.
 */
public interface AlbumSearchFragmentView {

    void viewSearchFilters(List<Filter> filterList);
    void viewSearchAlbum(AlbumSearchData albumSearchData);
    void showMessage(String msg);
    void showFilterSearchProgress(boolean state);
    void showAlbumSearchProgress(boolean state);
    void setNextPageUrl(String page);


}
