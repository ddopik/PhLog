package com.example.softmills.phlog.ui.album.view;

import com.example.softmills.phlog.ui.album.model.AlbumPreviewResponseData;

/**
 * Created by abdalla_maged on 11/6/2018.
 */
public interface AlbumPreviewActivityView {


    void viewAlumPreview(AlbumPreviewResponseData albumPreviewResponseData);

    void viewAlbumPreviewProgress(boolean state);
}
