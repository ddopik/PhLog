package com.example.softmills.phlog.ui.photographerprofile.editprofile.view;

import com.example.softmills.phlog.base.commonmodel.Photographer;

/**
 * Created by abdalla_maged On Nov,2018
 */
public interface EditPhotoGrapherProfileFragmentView {
    void showPhotoGrapherProfileData(Photographer photographer);
    void viewEditProfileProgress(Boolean State);

    void showMessage(int message);
}
