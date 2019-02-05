package com.example.softmills.phlog.ui.photographerprofile.view.ph_photos.view;

import com.example.softmills.phlog.base.commonmodel.BaseImage;

import java.util.List;

/**
 * Created by abdalla_maged on 10/1/2018.
 */
public interface FragmentPhotoGrapherPhotosView {
    void showPhotos(List<BaseImage> photosList);
    void showMessage(String msg);
    void showPhotosProgress(boolean state);
}
