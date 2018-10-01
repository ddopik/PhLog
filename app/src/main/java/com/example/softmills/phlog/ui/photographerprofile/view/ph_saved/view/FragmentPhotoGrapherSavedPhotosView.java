package com.example.softmills.phlog.ui.photographerprofile.view.ph_saved.view;

import com.example.softmills.phlog.ui.photographerprofile.view.ph_saved.model.PhotoGrapherSavedPhoto;

import java.util.List;

/**
 * Created by abdalla_maged on 9/30/2018.
 */
public interface FragmentPhotoGrapherSavedPhotosView {
    void showSavedPhotos(List<PhotoGrapherSavedPhoto> photosList);

    void showMessage(String msg);
}
