package com.example.softmills.phlog.ui.album.presenter;

import com.example.softmills.phlog.base.commonmodel.BaseImage;

/**
 * Created by abdalla_maged On Dec,2018
 */
public interface AllAlbumImgPresnter {

    void likePhoto(String photoId);

    void saveToProfileImage(BaseImage baseImage);
    void unSaveToProfileImage(BaseImage baseImage);
    void followImagePhotoGrapher(BaseImage baseImage);
}
