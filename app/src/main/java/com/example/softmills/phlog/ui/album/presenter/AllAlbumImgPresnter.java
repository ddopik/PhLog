package com.example.softmills.phlog.ui.album.presenter;

import com.example.softmills.phlog.base.commonmodel.BaseImage;

import java.util.List;

/**
 * Created by abdalla_maged On Dec,2018
 */
public interface AllAlbumImgPresnter {

    void deleteImage(BaseImage baseImage);
    void likePhoto(String photoId);
    void unLikePhoto(String photoId);

    void saveToProfileImage(BaseImage baseImage);
    void unSaveToProfileImage(BaseImage baseImage);
    void followImagePhotoGrapher(BaseImage baseImage);

    void getPhotoGrapherPhotosList(String page);
    void getPhotoGrapherSavedList(String page);

}
