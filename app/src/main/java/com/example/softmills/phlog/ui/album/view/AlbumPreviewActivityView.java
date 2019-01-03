package com.example.softmills.phlog.ui.album.view;

import com.example.softmills.phlog.base.commonmodel.BaseImage;
import com.example.softmills.phlog.ui.album.model.AlbumPreviewResponseData;

import java.util.List;

/**
 * Created by abdalla_maged on 11/6/2018.
 */
public interface AlbumPreviewActivityView {


    void viewAlumPreview(AlbumPreviewResponseData albumPreviewResponseData);

    void viewAlbumPreviewProgress(boolean state);
    void viwAlbumPreviewImages(List<BaseImage> baseImageList);
}
