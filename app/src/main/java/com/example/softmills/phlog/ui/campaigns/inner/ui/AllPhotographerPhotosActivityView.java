package com.example.softmills.phlog.ui.campaigns.inner.ui;

import com.example.softmills.phlog.base.commonmodel.BaseImage;

import java.util.List;

/**
 * Created by abdalla_maged On Dec,2018
 */
public interface AllPhotographerPhotosActivityView {

    void showSavedPhotos(List<BaseImage> photosList);
    void showMessage(String msg);
    void showSavedImageProgress(boolean state);
}
