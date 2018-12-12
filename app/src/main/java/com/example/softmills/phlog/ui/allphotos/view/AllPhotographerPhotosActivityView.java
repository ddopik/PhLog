package com.example.softmills.phlog.ui.allphotos.view;

import com.example.softmills.phlog.base.commonmodel.BaseImage;

import java.util.List;

/**
 * Created by abdalla_maged On Dec,2018
 */
public interface AllPhotographerPhotosActivityView {

    void showPhotosList(List<BaseImage> photosList);
    void showMessage(String msg);
    void showImageListProgress(boolean state);
}
