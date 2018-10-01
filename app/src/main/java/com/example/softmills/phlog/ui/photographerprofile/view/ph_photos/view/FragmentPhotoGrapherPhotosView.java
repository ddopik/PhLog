package com.example.softmills.phlog.ui.photographerprofile.view.ph_photos.view;

import com.example.softmills.phlog.ui.photographerprofile.view.ph_photos.model.PhotoGrapherPhoto;

import java.util.List;

/**
 * Created by abdalla_maged on 10/1/2018.
 */
public interface FragmentPhotoGrapherPhotosView {
    void showPhotos(List<PhotoGrapherPhoto> photosList);
    void showMessage(String msg);
    void showPhotosProgress(boolean state);
}
