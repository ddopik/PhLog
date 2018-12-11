package com.example.softmills.phlog.ui.photographerprofile.view.ph_saved.view;

import com.example.softmills.phlog.base.commonmodel.BaseImage;

import java.util.List;

/**
 * Created by abdalla_maged on 9/30/2018.
 */
public interface PhotoGrapherPhotosFragmentView {
    void showSavedPhotos(List<BaseImage> photosList);

    void showMessage(String msg);
}
