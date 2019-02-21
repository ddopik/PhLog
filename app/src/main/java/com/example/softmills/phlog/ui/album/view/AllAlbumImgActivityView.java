package com.example.softmills.phlog.ui.album.view;

import com.example.softmills.phlog.base.commonmodel.BaseImage;
import com.example.softmills.phlog.base.commonmodel.Photographer;

import java.util.List;

/**
 * Created by abdalla_maged on 11/5/2018.
 */
public interface AllAlbumImgActivityView  {

    void showMessage(String msg);

    void viewAlbumImageList(List<BaseImage> albumImgList);
    void viewAlbumImageListProgress(boolean state);

    void onImageSavedToProfile(BaseImage baseImage,boolean state);
    void onImagePhotoGrapherFollowed(BaseImage baseImage ,boolean state);
    void onImagePhotoGrapherDeleted(BaseImage baseImage ,boolean state);
}
