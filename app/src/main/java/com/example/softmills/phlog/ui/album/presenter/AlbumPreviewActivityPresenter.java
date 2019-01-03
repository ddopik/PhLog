package com.example.softmills.phlog.ui.album.presenter;

/**
 * Created by abdalla_maged on 11/6/2018.
 */
public interface AlbumPreviewActivityPresenter {


    void getSelectedSearchAlbum(int albumID, String pageNum);

    void getAlbumPreviewImages(int albumId, int page);

}
